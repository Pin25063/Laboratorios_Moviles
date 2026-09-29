package plat.pinto_ramirez.lab7.AppNavHost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import plat.pinto_ramirez.lab7.login.LoginDestination
import plat.pinto_ramirez.lab7.login.LoginScreen

@Serializable
object CharactersGraph

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
                        navController.navigate(CharacterDetailDestination(id = characterId)){
                        }
                    }
                )
            }

            composable<CharacterDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute<CharacterDetailDestination>()

                CharacterDetailScreen(
                    characterId = destination.id,
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}