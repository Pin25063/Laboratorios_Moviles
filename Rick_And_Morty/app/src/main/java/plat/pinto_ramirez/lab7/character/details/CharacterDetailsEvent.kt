package plat.pinto_ramirez.lab7.character.details

sealed interface CharacterDetailsEvent {
    data object onLoadingLayoutClick : CharacterDetailsEvent
    data object onRetryClick : CharacterDetailsEvent
    data class onFavoriteClick(val currentStatus: Boolean) : CharacterDetailsEvent
}