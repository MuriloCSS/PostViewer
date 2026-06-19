package br.edu.ifsp.scl.sc3038467.postviewer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifsp.scl.sc3038467.postviewer.viewmodel.PostViewModel


@Composable
fun TelaListaPosts(viewModel: PostViewModel, aoClicarNoPost: (Int) -> Unit) {
    val posts by viewModel.listaDePosts.collectAsState()
    val carregando by viewModel.carregando.collectAsState()
    val erro by viewModel.mensagemErro.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PostViewer",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (carregando) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        erro?.let { textoErro ->
            Text(
                text = textoErro,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(posts) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { aoClicarNoPost(post.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = post.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = post.body,
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun TelaDetalhesPost(viewModel: PostViewModel, postId: Int, aoVoltar: () -> Unit) {
    val comentarios by viewModel.listaDeComentarios.collectAsState()
    val carregando by viewModel.carregando.collectAsState()
    val erro by viewModel.mensagemErro.collectAsState()
    var textoDigitado by remember { mutableStateOf("") }

    LaunchedEffect(postId) {
        viewModel.carregarDetalhesDoPost(postId)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Comentários do Post $postId",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = aoVoltar,
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Voltar")
        }
        if (carregando) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        erro?.let { textoErro ->
            Text(
                text = textoErro,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            OutlinedTextField(
                value = textoDigitado,
                onValueChange = { textoDigitado = it },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.adicionarComentarioLocal(postId, textoDigitado)
                    textoDigitado = ""
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Salvar")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(comentarios) { comentario ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = comentario.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = comentario.autor, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = comentario.texto)


                    }
                }
            }
        }
    }
}

@Composable
fun AppNavegacao(viewModel: PostViewModel = viewModel()) {
    val controleNavegacao = rememberNavController()

    NavHost(navController = controleNavegacao, startDestination = "tela_lista") {
        composable("tela_lista") {
            TelaListaPosts(viewModel) { postId ->
                controleNavegacao.navigate("tela_detalhes/$postId")
            }
        }
        composable("tela_detalhes/{postId}") { backStackEntry ->
            val postIdString = backStackEntry.arguments?.getString("postId") ?: "1"
            TelaDetalhesPost(
                viewModel,
                postIdString.toInt()
            ) {
                controleNavegacao.popBackStack()
            }
        }
    }
}