package plat.pinto_ramirez.lab7.character.list

import plat.pinto_ramirez.lab7.Character

data class CharacterListState(
    val characterList: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val hasError : Boolean = false
)