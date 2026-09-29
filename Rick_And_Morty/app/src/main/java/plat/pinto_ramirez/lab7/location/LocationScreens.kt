package plat.pinto_ramirez.lab7.location

import LocationDb
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import plat.pinto_ramirez.lab7.AppNavHost.BottomBar

@Serializable
data object LocationsDestination

@Serializable
data class LocationDetailDestination(val id: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onCharactersClick:() -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit
    ) {

    val db = remember { LocationDb() }
    val locationList = remember { db.getAllLocations() }

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
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            items(
                items = locationList,
                key = {location -> location.id }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    locationId: Int,
    onBackClick: () -> Unit,
    onCharactersClick: () -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val db = remember { LocationDb() }
    val location = remember(locationId) { db.getLocationById(locationId) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Location details") },
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
                characters = false,
                locations = true,
                profile = false,
                onCharactersClick = onCharactersClick,
                onLocationsClick = onLocationsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        location?.let {
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = location.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.width(200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Text("ID:")
                        Text("Type:")
                        Text("Dimension:")
                    }
                    Column {
                        Text("${location.id}")
                        Text(location.type)
                        Text(location.dimension)
                    }
                }
            }
        }
    }
}