package plat.pinto_ramirez.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import plat.pinto_ramirez.lab6.ui.theme.Lab6Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainUi(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class ElementoHistorial(
    val numero: Int,
    val color: Color
)

data class Variables(
    val contador: Int = 0,
    val totalIncrementos: Int = 0,
    val totalDecrementos: Int = 0,
    val valMax: Int = 0,
    val valMin: Int = 0,
    val totalCambios: Int = 0,
    val historial: List<ElementoHistorial> = emptyList()
)

@Composable
fun CajaHistorial(numero: Int, color: Color) {
    Box(modifier = Modifier.background(color = color, shape = RoundedCornerShape(8.dp)).size(30.dp),){
        Text("$numero", modifier = Modifier.align(Alignment.Center), color = Color.White)
    }
}

@Composable
fun MainUi(modifier: Modifier = Modifier) {

    var variables by remember {
        mutableStateOf(Variables())
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("Jose Alejandro Pinto", fontSize = 32.sp)

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                val nuevoContador = variables.contador - 1
                val nuevoMin = if (nuevoContador < variables.valMin) nuevoContador else variables.valMin
                val nuevoElemento = ElementoHistorial(nuevoContador, Color(0xFFAD2E1C))

                variables = variables.copy(
                    contador = nuevoContador,
                    totalDecrementos = variables.totalDecrementos + 1,
                    totalCambios = variables.totalCambios + 1,
                    valMin =  nuevoMin,
                    historial = variables.historial + nuevoElemento,
                )

            }, modifier = Modifier.background(color = Color(0xFF455E90), shape = CircleShape)) { Text("-", color = Color.White, fontWeight = FontWeight.Bold) }

            Text("${variables.contador}", fontSize = 32.sp, fontWeight = FontWeight.Bold)

            IconButton(onClick = {
                val nuevoContador = variables.contador + 1
                val nuevoMax = if (nuevoContador > variables.valMax) nuevoContador else variables.valMax
                val nuevoElemento = ElementoHistorial(nuevoContador, Color(0xFF2C7D4E))

                variables = variables.copy(
                    contador = nuevoContador,
                    totalIncrementos = variables.totalIncrementos + 1,
                    totalCambios = variables.totalCambios + 1,
                    valMax =  nuevoMax,
                    historial = variables.historial + nuevoElemento,
                )
            }, modifier = Modifier.background(color = Color(0xFF455E90), shape = CircleShape)) { Text("+", color = Color.White, fontWeight = FontWeight.Bold)}
        }

        Spacer(modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(color = Color.Gray))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(75.dp, Alignment.CenterHorizontally)
        ) {
            Column{
                Text("Total Incrementos:", fontWeight = FontWeight.Bold)
                Text("Total Decrementos:", fontWeight = FontWeight.Bold)
                Text("Valor Maximo:", fontWeight = FontWeight.Bold)
                Text("Valor Minimo:", fontWeight = FontWeight.Bold)
                Text("Total Cambios:", fontWeight = FontWeight.Bold)
                Text("Historial:", fontWeight = FontWeight.Bold)
            }

            Column {
                Text("${variables.totalIncrementos}")
                Text("${variables.totalDecrementos}")
                Text("${variables.valMax}")
                Text("${variables.valMin}")
                Text("${variables.totalCambios}")
            }
        }

        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            maxItemsInEachRow = 5
        ) {
            for (elemento in variables.historial){
                CajaHistorial(elemento.numero, elemento.color)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { variables = Variables() },
            modifier = Modifier.fillMaxWidth(), // Hace que ocupe todo el ancho
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF455E90))
        ) {
            Text("Reiniciar", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Lab6Theme {
        MainUi(modifier = Modifier.fillMaxSize())
    }
}