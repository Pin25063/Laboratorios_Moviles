package plat.pinto_ramirez.lab7.character.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.AppNavHost.CharacterDetailDestination
import plat.pinto_ramirez.lab7.RepositoryProvider
import plat.pinto_ramirez.lab7.character.data.CharacterRepository

class CharacterDetailsViewModel @JvmOverloads constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository = RepositoryProvider.characterRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailsState(isLoading = true))
    val state: StateFlow<CharacterDetailsState> = _state.asStateFlow()

    private val characterId: Int = savedStateHandle.toRoute<CharacterDetailDestination>().id

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        _state.value = CharacterDetailsState(isLoading = true)
        viewModelScope.launch {
            val character = repository.getCharacterById(characterId)
            if (character != null) {
                _state.value = CharacterDetailsState(isLoading = false, character = character)
            } else {
                _state.value = CharacterDetailsState(isLoading = false, hasError = true)
            }
        }
    }

    fun OnEvent(event: CharacterDetailsEvent) {
        when (event) {
            is CharacterDetailsEvent.onLoadingLayoutClick -> {
                _state.value = CharacterDetailsState(
                    isLoading = false,
                    hasError = true
                )
            }
            is CharacterDetailsEvent.onRetryClick -> {
                loadCharacter()
            }
            is CharacterDetailsEvent.onFavoriteClick -> {
                viewModelScope.launch {
                    repository.updateFavoriteStatus(characterId, !event.currentStatus)
                    loadCharacter()
                }
            }
            is CharacterDetailsEvent.onEditCharacter -> {
                viewModelScope.launch {
                    val current = _state.value.character ?: return@launch
                    val updated = current.copy(
                        name = event.name,
                        species = event.species,
                        status = event.status,
                        gender = event.gender
                    )
                    repository.updateCharacter(updated)
                    loadCharacter()
                }
            }
            is CharacterDetailsEvent.onDeleteCharacter -> {
                viewModelScope.launch {
                    val current = _state.value.character ?: return@launch
                    repository.deleteCharacter(current)
                    event.onSuccess()
                }
            }
        }
    }
}
