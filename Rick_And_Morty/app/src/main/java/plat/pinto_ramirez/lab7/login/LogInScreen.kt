package plat.pinto_ramirez.lab7.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import plat.pinto_ramirez.lab7.R

@Serializable
data object LoginDestination

@Composable
fun LoginScreen (
    onNavigateToCharacters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.weight(2f)
        )
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo Rick and Morty",
            modifier = Modifier
                .height(200.dp)
                .width(400.dp)
        )
        Button(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            onClick = onNavigateToCharacters
        ) {
            Text("Entrar")
        }
        Spacer(
            modifier = Modifier.weight(2f)
        )
        Text("Jose Pinto - 25063",
            modifier = Modifier.weight(1f))
    }
}