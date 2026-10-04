package plat.pinto_ramirez.lab7.character.details

import plat.pinto_ramirez.lab7.Character

data class CharacterDetailsState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false
)