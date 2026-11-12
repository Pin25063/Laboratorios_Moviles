package plat.pinto_ramirez.lab7.location.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import plat.pinto_ramirez.lab7.AppNavHost.BottomBar
import plat.pinto_ramirez.lab7.ErrorScreen
import plat.pinto_ramirez.lab7.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    state: LocationListState = LocationListState(),
    onEvent: (LocationListEvents) -> Unit = {},
    onCharactersClick: () -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val locationList = state.locations

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        },
        bottomBar = {
            BottomBar(
                characters = false,
                locations = true,
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
                onLoadingClick = { onEvent(LocationListEvents.onLoadingLayoutClick) }
            )
        } else if (state.hasError) {
            ErrorScreen(
                modifier = Modifier.padding(innerPadding),
                onRetryClick = { onEvent(LocationListEvents.onRetryClick) }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterChip(
                        selected = state.selectedFilter == LocationFilter.ALL,
                        onClick = { onEvent(LocationListEvents.onFilterSelected(LocationFilter.ALL)) },
                        label = { Text("Todas") }
                    )
                    FilterChip(
                        selected = state.selectedFilter == LocationFilter.FAVORITES,
                        onClick = { onEvent(LocationListEvents.onFilterSelected(LocationFilter.FAVORITES)) },
                        label = { Text("Favoritas") }
                    )
                    FilterChip(
                        selected = state.selectedFilter == LocationFilter.NON_FAVORITES,
                        onClick = { onEvent(LocationListEvents.onFilterSelected(LocationFilter.NON_FAVORITES)) },
                        label = { Text("No Favoritas") }
                    )
                }

                if (locationList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay elementos a mostrar", style = MaterialTheme.typography.titleMedium)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        items(
                            items = locationList,
                            key = { location -> location.id }
                        ) { location ->
                            HorizontalDivider()
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = { onLocationClick(location.id) })
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = location.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = location.type, style = MaterialTheme.typography.bodyMedium)
                                }
                                IconButton(
                                    onClick = {
                                        onEvent(
                                            LocationListEvents.onFavoriteClick(
                                                location.id,
                                                location.isFavorite
                                            )
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (location.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorito",
                                        tint = if (location.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
