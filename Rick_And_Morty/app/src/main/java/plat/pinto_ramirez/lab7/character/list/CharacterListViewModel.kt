package plat.pinto_ramirez.lab7.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.DatabaseProvider
import plat.pinto_ramirez.lab7.character.data.toModel

class CharacterListViewModel : ViewModel() {
    private val db = DatabaseProvider.db

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
            val entities = db.characterDao().getAllCharacters()
            var models = entities.map { it.toModel() }

            if (_showOnlyFavorites.value) {
                models = models.filter { it.isFavorite }
            }

            _state.value = CharacterListState(
                isLoading = false,
                characterList = models
            )
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
                    db.characterDao().updateFavoriteStatus(event.characterId, !event.currentStatus)
                    loadCharacters()
                }
            }
            CharacterListEvent.onToggleFavoriteFilter -> {
                _showOnlyFavorites.value = !_showOnlyFavorites.value
                loadCharacters()
            }
        }
    }
}