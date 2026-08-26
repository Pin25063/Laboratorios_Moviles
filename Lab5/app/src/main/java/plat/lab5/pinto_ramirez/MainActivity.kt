package plat.lab5.pinto_ramirez

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import plat.lab5.pinto_ramirez.ui.theme.Lab5Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UiPrincipal(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun UiPrincipal(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = {},
                shape = CircleShape,
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                )
                {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Actualizar Aplicacion",
                    tint = MaterialTheme.colorScheme.secondaryContainer
                )}
            Text("Actualización disponible")
            TextButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fridays.guatemala&hl=es_GT&pli=1"))
                context.startActivity(intent)
            }) {
                Text(
                    text = "Descargar",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Martes", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
                Text("22 de Diciembre")
            }
            OutlinedButton(onClick = {},shape = RoundedCornerShape(8.dp)) {
                Text("Terminar Jornada", color = MaterialTheme.colorScheme.secondary)
            }
        }

        Box() {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ){
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("TGI FRIDAYS", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q=TGI+FRIDAYS+Majadas"))
                        context.startActivity(intent)
                    }) { Icon(
                        imageVector = Icons.Default.Directions,
                        contentDescription = "Ir al mapa",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.fillMaxSize()
                    ) }
                }
                Text("Majadas, 8 calle, Zona 11")
                Text("12 P.M. -> 10 P.M.", modifier = Modifier.alpha(0.6F))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {Toast.makeText(context, "Jose Alejandro Pinto Ramirez", Toast.LENGTH_SHORT).show()},
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Iniciar")
                    }

                    TextButton(onClick = {
                        Toast.makeText(context,"Comida variada\nNormal: QQ", Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Detalles",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun GreetingPreview() {
    Lab5Theme {
        UiPrincipal(modifier = Modifier.fillMaxSize())
    }
}