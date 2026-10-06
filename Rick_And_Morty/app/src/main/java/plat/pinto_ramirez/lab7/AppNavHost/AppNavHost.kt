package plat.pinto_ramirez.lab7.AppNavHost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.pinto_ramirez.lab7.character.details.CharacterDetailScreen
import plat.pinto_ramirez.lab7.character.details.CharacterDetailsViewModel
import plat.pinto_ramirez.lab7.character.list.CharacterListViewModel
import plat.pinto_ramirez.lab7.character.list.CharacterScreen
import plat.pinto_ramirez.lab7.location.details.LocationDetailScreen
import plat.pinto_ramirez.lab7.location.details.LocationDetailsViewModel
import plat.pinto_ramirez.lab7.location.list.LocationListViewModel
import plat.pinto_ramirez.lab7.location.list.LocationsScreen
import plat.pinto_ramirez.lab7.login.LoginDestination
import plat.pinto_ramirez.lab7.login.LoginScreen
import plat.pinto_ramirez.lab7.profile.ProfileDestination
import plat.pinto_ramirez.lab7.profile.ProfileScreen

@Serializable
object CharactersGraph

@Serializable
data object CharactersDestination

@Serializable
data class CharacterDetailDestination(val id: Int)

@Serializable
object LocationsGraph

@Serializable
data object LocationsDestination

@Serializable
data class LocationDetailDestination(val id: Int)

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
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
                    navController.navigate(CharactersGraph){
                        popUpTo<LoginDestination> { inclusive = true }
                    }
                }
            )
        }

        navigation<CharactersGraph>(
            startDestination = CharactersDestination
        ){
            composable<CharactersDestination>{
                val viewModel = viewModel<CharacterListViewModel>()
                val state by viewModel.state.collectAsState()

                CharacterScreen(
                    state = state,
                    onEvent = { event -> viewModel.OnEvent(event) },
                    modifier = Modifier.fillMaxSize(),
                    onCharacterClick = { characterId ->
                        navController.navigate(CharacterDetailDestination(id = characterId))
                    },
                    onCharactersClick = { },
                    onLocationsClick = { navController.navigate(LocationsGraph) },
                    onProfileClick = { navController.navigate(ProfileDestination) }
                )
            }

            composable<CharacterDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute<CharacterDetailDestination>()
                val viewModel = viewModel<CharacterDetailsViewModel>()
                val state by viewModel.state.collectAsState()

                CharacterDetailScreen(
                    state = state,
                    onEvent = { event -> viewModel.OnEvent(event) },
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize(),
                    onCharactersClick = { },
                    onLocationsClick = { navController.navigate(LocationsGraph) },
                    onProfileClick = { navController.navigate(ProfileDestination) }
                )
            }
        }

        navigation<LocationsGraph>(
            startDestination = LocationsDestination
        ){
            composable<LocationsDestination>{
                val viewModel = viewModel<LocationListViewModel>()
                val state by viewModel.state.collectAsState()

                LocationsScreen(
                    state = state,
                    onEvent = { event -> viewModel.onEvent(event) },
                    onLocationClick = { locationId ->
                        navController.navigate(LocationDetailDestination(id = locationId))
                    },
                    onCharactersClick = { navController.navigate(CharactersGraph) },
                    onLocationsClick = { },
                    onProfileClick = { navController.navigate(ProfileDestination) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<LocationDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute<LocationDetailDestination>()

                val viewModel = viewModel<LocationDetailsViewModel>()
                val state by viewModel.state.collectAsState()

                LocationDetailScreen(
                    state = state,
                    onEvent = { event -> viewModel.onEvent(event) },
                    onBackClick = { navController.popBackStack() },
                    onCharactersClick = { navController.navigate(CharactersGraph) },
                    onLocationsClick = { },
                    onProfileClick = { navController.navigate(ProfileDestination) }
                )
            }
        }

        composable<ProfileDestination>{
            ProfileScreen(
                onCharactersClick = { navController.navigate(CharactersGraph) },
                onLocationsClick = { navController.navigate(LocationsGraph) },
                onProfileClick = { },
                onLogOutClick = {
                    navController.navigate(LoginDestination) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBar(
    characters: Boolean,
    locations: Boolean,
    profile: Boolean,
    onCharactersClick: () -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
        unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
        unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        NavigationBarItem(
            selected = characters,
            onClick = onCharactersClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = "Characters"
                )
            },
            label = {
                Text(
                    text = "Characters",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = if (characters) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = itemColors
        )

        NavigationBarItem(
            selected = locations,
            onClick = onLocationsClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = "Locations"
                )
            },
            label = {
                Text(
                    text = "Locations",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = if (locations) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = itemColors
        )

        NavigationBarItem(
            selected = profile,
            onClick = onProfileClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text(
                    text = "Profile",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = if (profile) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = itemColors
        )
    }
}