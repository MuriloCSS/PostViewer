package br.edu.ifsp.scl.sc3038467.postviewer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.edu.ifsp.scl.sc3038467.postviewer.data.AppBancoDeDados
import br.edu.ifsp.scl.sc3038467.postviewer.data.ClienteRetrofit
import br.edu.ifsp.scl.sc3038467.postviewer.model.ComentarioTela
import br.edu.ifsp.scl.sc3038467.postviewer.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

}
