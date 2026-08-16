package plat.lab4.lab4
import plat.lab4.lab4.ui.theme.Lab4Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width


@Composable
fun UiPrincipal(modifier: Modifier = Modifier) {

    Column(

        modifier = modifier,

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally

    ) {



        Column (

            modifier = Modifier,

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.SpaceEvenly,

            ) {

            Text(text = "Universidad del Valle de Guatemala", fontSize = 35.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

            Text(text = "\nProgramación de Plataformas moviles, Sección 30", fontSize = 30.sp, textAlign = TextAlign.Center)

        }



        Spacer(modifier = Modifier.height(16.dp))



        Row(

            modifier = Modifier,

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceEvenly,

            ) {

            Column() { Text(text = "Integrantes", fontWeight = FontWeight.Bold) }

            Spacer(modifier = Modifier.width(50.dp))

            Column() { Text("Jose Pinto\nCristian Ronaldo\nLeonel Pessi") }

        }



        Spacer(modifier = Modifier.height(16.dp))



        Row(

            modifier = Modifier,

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceEvenly,

            ) {

            Column() { Text(text = "Catedratico", fontWeight = FontWeight.Bold) }

            Spacer(modifier = Modifier.width(50.dp))

            Column() { Text("Juan Carlos Durini") }

        }



        Spacer(modifier = Modifier.height(16.dp))



        Column (

            modifier = Modifier,

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.SpaceEvenly

        ) {

            Text("Jose Pinto ")

            Text("\n25063")

        }

    }
}



@Preview(showBackground = true)

@Composable

fun UiPrincipalPreview() {

    Lab4Theme {

        UiPrincipal(modifier = Modifier.fillMaxSize().padding(30.dp))

    }

} 

