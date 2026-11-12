package plat.pinto_ramirez.lab7.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.Character
import plat.pinto_ramirez.lab7.RepositoryProvider
import plat.pinto_ramirez.lab7.character.data.CharacterRepository

class CharacterListViewModel(
    private val repository: CharacterRepository = RepositoryProvider.characterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState(isLoading = true))
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    private val _showOnlyFavorites = MutableStateFlow(false)
    val showOnlyFavorites: StateFlow<Boolean> = _showOnlyFavorites.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val characters = repository.getCharacters()
                val models = if (_showOnlyFavorites.value) {
                    characters.filter { it.isFavorite }
                } else {
                    characters
                }

                _state.value = CharacterListState(
                    isLoading = false,
                    characterList = models
                )
            } catch (e: Exception) {
                _state.value = CharacterListState(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    fun OnEvent(event: CharacterListEvent) {
        when (event) {
            is CharacterListEvent.onCharacterClick -> {}
            CharacterListEvent.onLoadingLayoutClick -> {
                _state.value = CharacterListState(isLoading = false, hasError = true)
            }
            CharacterListEvent.onRetryClick -> loadCharacters()

            is CharacterListEvent.onFavoriteClick -> {
                viewModelScope.launch {
                    repository.updateFavoriteStatus(event.characterId, !event.currentStatus)
                    loadCharacters()
                }
            }
            CharacterListEvent.onToggleFavoriteFilter -> {
                _showOnlyFavorites.value = !_showOnlyFavorites.value
                loadCharacters()
            }
            CharacterListEvent.onRefresh -> {
                loadCharacters()
            }
            is CharacterListEvent.onCreateCharacter -> {
                viewModelScope.launch {
                    val all = repository.getCharacters()
                    val nextId = (all.maxOfOrNull { it.id } ?: 0) + 1
                    val newCharacter = Character(
                        id = nextId,
                        name = event.name,
                        status = event.status,
                        species = event.species,
                        gender = event.gender,
                        image = "https://rickandmortyapi.com/api/character/avatar/19.jpeg",
                        isFavorite = false
                    )
                    repository.insertCharacter(newCharacter)
                    loadCharacters()
                }
            }
            is CharacterListEvent.onDeleteCharacter -> {
                viewModelScope.launch {
                    repository.deleteCharacter(event.character)
                    loadCharacters()
                }
            }
        }
    }
}
