package plat.pinto_ramirez.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import plat.pinto_ramirez.lab7.ui.theme.Lab7Theme
import plat.pinto_ramirez.lab7.AppNavHost.AppNavHost


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab7Theme {
                    AppNavHost(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }