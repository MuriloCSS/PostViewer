package br.edu.ifsp.scl.sc3038467.postviewer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3038467.postviewer.data.AppBancoDeDados
import br.edu.ifsp.scl.sc3038467.postviewer.data.ClienteRetrofit
import br.edu.ifsp.scl.sc3038467.postviewer.data.ComentarioLocal
import br.edu.ifsp.scl.sc3038467.postviewer.model.ComentarioTela
import br.edu.ifsp.scl.sc3038467.postviewer.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(aplicacao: Application) : AndroidViewModel(aplicacao) {

    private val api = ClienteRetrofit.api
    private val dao = AppBancoDeDados.Companion.obterInstancia(aplicacao).comentarioDao()

    private val _listaDePosts = MutableStateFlow<List<Post>>(emptyList())
    val listaDePosts: StateFlow<List<Post>> = _listaDePosts.asStateFlow()

    private val _listaDeComentarios = MutableStateFlow<List<ComentarioTela>>(emptyList())
    val listaDeComentarios: StateFlow<List<ComentarioTela>> = _listaDeComentarios.asStateFlow()

    private val _carregando = MutableStateFlow(false)
    val carregando: StateFlow<Boolean> = _carregando.asStateFlow()

    private val _mensagemErro = MutableStateFlow<String?>(null)
    val mensagemErro: StateFlow<String?> = _mensagemErro.asStateFlow()

    init {
        carregarPosts()
    }

    private fun carregarPosts() {
        viewModelScope.launch {
            _carregando.value = true
            _mensagemErro.value = null
            try {
                val postsDaApi = api.buscarPosts()
                val comentariosApi = api.buscarTodosComentarios()
                val mapaApi = comentariosApi.groupBy { it.postId }
                val postsAtualizados = postsDaApi.map { post ->
                    val total = (mapaApi[post.id]?.size ?: 0)
                    post.copy(quantidadeComentarios = total)
                }
                _listaDePosts.value = postsAtualizados
            } catch (e: Exception) {
                _mensagemErro.value = "Erro ao carregar os posts"
            } finally {
                _carregando.value = false
            }
        }
    }


    fun carregarDetalhesDoPost(postId: Int) {
        viewModelScope.launch {
            _carregando.value = true
            _mensagemErro.value = null
            try {
                val comentariosLocais = dao.buscarComentariosDoPost(postId)
                val comentariosDaApi = api.buscarComentarios(postId)
                val listaUnida = mutableListOf<ComentarioTela>()
                comentariosLocais.forEach { local ->
                    listaUnida.add(ComentarioTela(titulo = "Comentário Local", autor = "Você", texto = local.texto))
                }
                comentariosDaApi.forEach { remoto ->
                    listaUnida.add(ComentarioTela(titulo = remoto.name, autor = remoto.email, texto = remoto.body))
                }
                _listaDeComentarios.value = listaUnida
            } catch (e: Exception) {
                _mensagemErro.value = "Erro ao carregar os comentários."
            } finally {
                _carregando.value = false
            }
        }
    }

    fun adicionarComentarioLocal(postId: Int, textoDigitado: String) {
        if (textoDigitado.isBlank()) return

        viewModelScope.launch {
            val novoComentario = ComentarioLocal(postId = postId, texto = textoDigitado)
            dao.salvarComentario(novoComentario)

            carregarDetalhesDoPost(postId)
        }
    }

}
