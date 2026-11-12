package plat.pinto_ramirez.lab7.character.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var showCreateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(CharacterListEvent.onRefresh)
    }

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Crear Personaje")
            }
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

    if (showCreateDialog) {
        var name by remember { mutableStateOf("") }
        var species by remember { mutableStateOf("") }
        var status by remember { mutableStateOf("Alive") }
        var gender by remember { mutableStateOf("Male") }

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Crear Personaje") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = species,
                        onValueChange = { species = it },
                        label = { Text("Especie") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = status,
                        onValueChange = { status = it },
                        label = { Text("Estado (Alive, Dead, unknown)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = gender,
                        onValueChange = { gender = it },
                        label = { Text("Género (Male, Female, unknown)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onEvent(
                                CharacterListEvent.onCreateCharacter(
                                    name = name.trim(),
                                    species = species.ifBlank { "Unknown" }.trim(),
                                    status = status.ifBlank { "unknown" }.trim(),
                                    gender = gender.ifBlank { "unknown" }.trim()
                                )
                            )
                            showCreateDialog = false
                        }
                    }
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
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
