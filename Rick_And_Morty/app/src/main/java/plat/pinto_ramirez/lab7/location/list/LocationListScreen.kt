package plat.pinto_ramirez.lab7.location.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    onCharactersClick:() -> Unit,
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
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,)
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
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                items(
                    items = locationList,
                    key = { location -> location.id }
                ) { location ->
                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable(
                                onClick = { onLocationClick(location.id) }
                            )
                    ) {
                        Text(text = location.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = location.type, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}