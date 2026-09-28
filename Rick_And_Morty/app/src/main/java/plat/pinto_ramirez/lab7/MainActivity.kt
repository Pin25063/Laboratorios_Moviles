package plat.pinto_ramirez.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import plat.pinto_ramirez.lab7.ui.theme.Lab7Theme
import kotlinx.serialization.Serializable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.composable
import androidx.navigation.toRoute


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab7Theme {
                    MainUi(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier) {

    val db = remember { CharacterDb() }
    val characterList = remember { db.getAllCharacters() }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Characters") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ){
            items(
                items = characterList,
                key = {character -> character.id}
            ) { character ->
                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                CharacterBox(
                    urlFoto = character.image,
                    name = character.name,
                    species = character.species,
                    status = character.status,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            onCharacterClick(character.id)
                        })
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(characterId: Int,
                          onBackClick: () -> Unit,
                          modifier: Modifier = Modifier) {
    val db = remember { CharacterDb() }
    val character = remember(characterId) { db.getCharacterById(characterId) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Character Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        character?.let {
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(20.dp))
                AsyncImage(
                    model = character.image,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
                Text("${character.name}", fontWeight = FontWeight.Bold)
                Row (
                    modifier = Modifier.width(200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column() {
                        Text("Species:")
                        Text("Status:")
                        Text("Gender:")
                    }
                    Column() {
                        Text("${character.species}")
                        Text("${character.status}")
                        Text("${character.gender}")
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterBox(modifier: Modifier = Modifier, urlFoto: String, name: String, species: String, status: String) {
    Row(modifier = modifier) {
        AsyncImage(
            model = urlFoto,
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column() {
            Text("$name", fontWeight = FontWeight.Bold)
            Text("$species - $status")
        }
    }
}

@Composable
fun MainUi(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        modifier = modifier
    ) {
        composable<LoginDestination>{
            LoginScreen(
                modifier = Modifier,
                onNavigateToCharacters = {
                    navController.navigate(CharactersDestination){
                        popUpTo<LoginDestination> { inclusive = true }
                    }
                }
            )
        }

        composable<CharactersDestination>{
            CharacterScreen(
                modifier = Modifier.fillMaxSize(),
                onCharacterClick = { characterId ->
                    navController.navigate(CharacterDetailDestination(id = characterId)){
                    }
                }
            )
        }

        composable<CharacterDetailDestination> { backStackEntry ->
            val destination = backStackEntry.toRoute<CharacterDetailDestination>()

            CharacterDetailScreen(
                characterId = destination.id,
                onBackClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Serializable
data object LoginDestination

@Serializable
data object CharactersDestination

@Serializable
data class CharacterDetailDestination(val id: Int)