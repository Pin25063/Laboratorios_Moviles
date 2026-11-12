package plat.pinto_ramirez.lab7.character.list

import plat.pinto_ramirez.lab7.Character

sealed interface CharacterListEvent {
    data class onCharacterClick(val characterId: Int) : CharacterListEvent
    data object onLoadingLayoutClick : CharacterListEvent
    data object onRetryClick : CharacterListEvent
    data class onFavoriteClick(val characterId: Int, val currentStatus: Boolean) : CharacterListEvent
    data object onToggleFavoriteFilter : CharacterListEvent
    data object onRefresh : CharacterListEvent
    data class onCreateCharacter(
        val name: String,
        val species: String,
        val status: String,
        val gender: String
    ) : CharacterListEvent
    data class onDeleteCharacter(val character: Character) : CharacterListEvent
}
