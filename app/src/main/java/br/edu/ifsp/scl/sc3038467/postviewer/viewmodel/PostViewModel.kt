package br.edu.ifsp.scl.sc3038467.postviewer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3038467.postviewer.data.AppBancoDeDados
import br.edu.ifsp.scl.sc3038467.postviewer.data.ClienteRetrofit
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
                _listaDePosts.value = postsDaApi
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
                    listaUnida.add(ComentarioTela(autor = "Você(Comentario Local)", texto = local.texto))
                }
                comentariosDaApi.forEach { remoto ->
                    listaUnida.add(ComentarioTela(autor = remoto.email, texto = remoto.body))
                }
                _listaDeComentarios.value = listaUnida
            } catch (e: Exception) {
                _mensagemErro.value = "Erro ao carregar os comentários."
            } finally {
                _carregando.value = false
            }
        }
    }

}
