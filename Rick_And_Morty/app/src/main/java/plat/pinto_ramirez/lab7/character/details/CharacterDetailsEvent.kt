package plat.pinto_ramirez.lab7.character.details

sealed interface CharacterDetailsEvent {
    data object onLoadingLayoutClick : CharacterDetailsEvent
    data object onRetryClick : CharacterDetailsEvent
    data class onFavoriteClick(val currentStatus: Boolean) : CharacterDetailsEvent
    data class onEditCharacter(
        val name: String,
        val species: String,
        val status: String,
        val gender: String
    ) : CharacterDetailsEvent
    data class onDeleteCharacter(val onSuccess: () -> Unit) : CharacterDetailsEvent
}
