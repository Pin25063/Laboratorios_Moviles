package plat.pinto_ramirez.lab7.character.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    onCharactersClick: () -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val characterList = state.characterList

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Characters") },
                actions = {
                    IconButton(onClick = { onEvent(CharacterListEvent.onToggleFavoriteFilter) }) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filtrar favoritos")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomBar(true, false, false, onCharactersClick, onLocationsClick, onProfileClick)
        }
    ) { innerPadding ->
        if (state.isLoading) {
            LoadingScreen(modifier = Modifier.padding(innerPadding)) { onEvent(CharacterListEvent.onLoadingLayoutClick) }
        } else if (state.hasError) {
            ErrorScreen(modifier = Modifier.padding(innerPadding)) { onEvent(CharacterListEvent.onRetryClick) }
        } else if (characterList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("No hay elementos a mostrar", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                items(items = characterList, key = { it.id }) { character ->
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                    CharacterBox(
                        urlFoto = character.image,
                        name = character.name,
                        species = character.species,
                        status = character.status,
                        isFavorite = character.isFavorite,
                        onFavoriteClick = { onEvent(CharacterListEvent.onFavoriteClick(character.id, character.isFavorite)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { onCharacterClick(character.id) })
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterBox(
    modifier: Modifier = Modifier,
    urlFoto: String, name: String, species: String, status: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = urlFoto,
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text("$species - $status")
            }
        }
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorito",
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}