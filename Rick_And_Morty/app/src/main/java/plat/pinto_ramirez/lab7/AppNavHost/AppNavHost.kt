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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.pinto_ramirez.lab7.character.CharacterDetailDestination
import plat.pinto_ramirez.lab7.character.CharactersDestination
import plat.pinto_ramirez.lab7.character.CharacterDetailScreen
import plat.pinto_ramirez.lab7.character.CharacterScreen
import plat.pinto_ramirez.lab7.location.LocationDetailDestination
import plat.pinto_ramirez.lab7.location.LocationDetailScreen
import plat.pinto_ramirez.lab7.location.LocationsDestination
import plat.pinto_ramirez.lab7.location.LocationsScreen
import plat.pinto_ramirez.lab7.login.LoginDestination
import plat.pinto_ramirez.lab7.login.LoginScreen
import plat.pinto_ramirez.lab7.profile.ProfileDestination
import plat.pinto_ramirez.lab7.profile.ProfileScreen

@Serializable
object CharactersGraph

@Serializable
object LocationsGraph

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
                CharacterScreen(
                    modifier = Modifier.fillMaxSize(),
                    onCharacterClick = { characterId ->
                        navController.navigate(CharacterDetailDestination(id = characterId)) {
                        }
                    },
                    onCharactersClick = { },
                    onLocationsClick = { navController.navigate(LocationsGraph) },
                    onProfileClick = { navController.navigate(ProfileDestination) }
                )
            }

            composable<CharacterDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute<CharacterDetailDestination>()

                CharacterDetailScreen(
                    characterId = destination.id,
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
                LocationsScreen(
                    onLocationClick = { locationId ->
                        navController.navigate(LocationDetailDestination(id = locationId))
                    },
                    onCharactersClick = { navController.navigate(CharactersGraph) },
                    onLocationsClick = { },
                    onProfileClick = { navController.navigate(ProfileDestination) },
                    modifier = Modifier
                )
            }

            composable<LocationDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute<LocationDetailDestination>()

                LocationDetailScreen(
                    locationId = destination.id,
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
        // Pestaña Characters
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

        // Pestaña Locations
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

        // Pestaña Profile
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