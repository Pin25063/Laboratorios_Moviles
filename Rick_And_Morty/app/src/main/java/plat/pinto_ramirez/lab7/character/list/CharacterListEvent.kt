package plat.pinto_ramirez.lab7.character.list

sealed interface CharacterListEvent{
    data class onCharacterClick (val characterId: Int) : CharacterListEvent
    data object onLoadingLayoutClick : CharacterListEvent
    data object onRetryClick : CharacterListEvent
}