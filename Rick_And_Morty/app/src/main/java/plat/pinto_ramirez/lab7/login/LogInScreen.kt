package plat.pinto_ramirez.lab7.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    viewModel: LoginViewModel,
    onNavigateToCharacters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userName by viewModel.userName.collectAsState()
    val navigate by viewModel.navigateToCharacters.collectAsState()

    LaunchedEffect(navigate) {
        if (navigate) {
            onNavigateToCharacters()
            viewModel.onNavigationDone()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(2f))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo Rick and Morty",
            modifier = Modifier
                .height(200.dp)
                .width(400.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userName,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Ingresa tu nombre") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            onClick = { viewModel.login() }
        ) {
            Text("Entrar")
        }
        Spacer(modifier = Modifier.weight(2f))
        Text("Jose Pinto - 25063", modifier = Modifier.padding(bottom = 16.dp))
    }
}