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
                        Text(text = post.title, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
