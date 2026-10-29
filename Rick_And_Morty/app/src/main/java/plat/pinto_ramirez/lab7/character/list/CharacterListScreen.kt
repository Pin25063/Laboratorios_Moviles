package plat.pinto_ramirez.lab7.character.list

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import plat.pinto_ramirez.lab7.AppNavHost.BottomBar
import plat.pinto_ramirez.lab7.ErrorScreen
import plat.pinto_ramirez.lab7.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    state: CharacterListState = CharacterListState(),
    onEvent: (CharacterListEvent) -> Unit = {},
    onCharactersClick:() -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val characterList = state.characterList

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

        if (state.isLoading) {
            LoadingScreen(
                modifier = Modifier.padding(innerPadding),
                onLoadingClick = { onEvent(CharacterListEvent.onLoadingLayoutClick) }
            )
        } else if (state.hasError) {
            ErrorScreen(
                modifier = Modifier.padding(innerPadding),
                onRetryClick = { onEvent(CharacterListEvent.onRetryClick) }
            )
        }

        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                items(
                    items = characterList,
                    key = { character -> character.id }
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