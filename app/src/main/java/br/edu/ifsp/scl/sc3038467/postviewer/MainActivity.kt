package br.edu.ifsp.scl.sc3038467.postviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.ifsp.scl.sc3038467.postviewer.ui.theme.PostViewerTheme
import br.edu.ifsp.scl.sc3038467.postviewer.ui.AppNavegacao

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostViewerTheme() {
                AppNavegacao()
            }
        }
    }
}
