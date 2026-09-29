package plat.pinto_ramirez.lab7.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
import plat.pinto_ramirez.lab7.AppNavHost.BottomBar
import plat.pinto_ramirez.lab7.CharacterDb

@Serializable
data object CharactersDestination

@Serializable
data class CharacterDetailDestination(val id: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onCharactersClick:() -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit
    ) {

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
        },
        bottomBar = {
            BottomBar(
                characters = true,
                locations = false,
                profile = false,
                onCharactersClick = onCharactersClick,
                onLocationsClick = onLocationsClick,
                onProfileClick = onProfileClick
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
                          onCharactersClick: () -> Unit,
                          onLocationsClick: () -> Unit,
                          onProfileClick: () -> Unit,
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
        },
        bottomBar = {
            BottomBar(
                characters = true,
                locations = false,
                profile = false,
                onCharactersClick = onCharactersClick,
                onLocationsClick = onLocationsClick,
                onProfileClick = onProfileClick
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