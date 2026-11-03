package sv.edu.udb.dsm_desafio03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import sv.edu.udb.dsm_desafio03.core.ui.theme.DSMDesafio03Theme
import sv.edu.udb.dsm_desafio03.navigation.AppNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DSMDesafio03Theme {
                AppNavHost()
            }
        }
    }
}