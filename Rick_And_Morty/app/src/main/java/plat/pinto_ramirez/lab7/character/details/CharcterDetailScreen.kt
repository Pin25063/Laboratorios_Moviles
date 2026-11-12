package plat.pinto_ramirez.lab7.character.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
fun CharacterDetailScreen(
    state: CharacterDetailsState = CharacterDetailsState(),
    onEvent: (CharacterDetailsEvent) -> Unit = {},
    onBackClick: () -> Unit,
    onCharactersClick: () -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val character = state.character
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

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
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = {
                        onEvent(CharacterDetailsEvent.onFavoriteClick(character?.isFavorite ?: false))
                    }) {
                        Icon(
                            imageVector = if (character?.isFavorite == true) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Outlined.FavoriteBorder
                            },
                            contentDescription = "Favorito"
                        )
                    }
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar"
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar"
                        )
                    }
                }
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
                onLoadingClick = { onEvent(CharacterDetailsEvent.onLoadingLayoutClick) }
            )
        } else if (state.hasError) {
            ErrorScreen(
                modifier = Modifier.padding(innerPadding),
                onRetryClick = { onEvent(CharacterDetailsEvent.onRetryClick) }
            )
        } else {
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
                    Text(character.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Row(
                        modifier = Modifier.width(220.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Species:", fontWeight = FontWeight.SemiBold)
                            Text("Status:", fontWeight = FontWeight.SemiBold)
                            Text("Gender:", fontWeight = FontWeight.SemiBold)
                        }
                        Column {
                            Text(character.species)
                            Text(character.status)
                            Text(character.gender)
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog && character != null) {
        var editName by remember { mutableStateOf(character.name) }
        var editSpecies by remember { mutableStateOf(character.species) }
        var editStatus by remember { mutableStateOf(character.status) }
        var editGender by remember { mutableStateOf(character.gender) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar Personaje") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editSpecies,
                        onValueChange = { editSpecies = it },
                        label = { Text("Especie") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editStatus,
                        onValueChange = { editStatus = it },
                        label = { Text("Estado") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editGender,
                        onValueChange = { editGender = it },
                        label = { Text("Género") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editName.isNotBlank()) {
                            onEvent(
                                CharacterDetailsEvent.onEditCharacter(
                                    name = editName.trim(),
                                    species = editSpecies.trim(),
                                    status = editStatus.trim(),
                                    gender = editGender.trim()
                                )
                            )
                            showEditDialog = false
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showDeleteDialog && character != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Personaje") },
            text = { Text("¿Estás seguro de que deseas eliminar a ${character.name}?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onEvent(CharacterDetailsEvent.onDeleteCharacter(onSuccess = onBackClick))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
