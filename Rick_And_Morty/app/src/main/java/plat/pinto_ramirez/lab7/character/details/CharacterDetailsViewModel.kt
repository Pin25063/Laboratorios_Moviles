package plat.pinto_ramirez.lab7.character.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.AppNavHost.CharacterDetailDestination
import plat.pinto_ramirez.lab7.character.data.CharacterDb

class CharacterDetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val db = CharacterDb()
    private val _state = MutableStateFlow(CharacterDetailsState(isLoading = true))
    val state: StateFlow<CharacterDetailsState> = _state.asStateFlow()

    private val characterId: Int = savedStateHandle.toRoute<CharacterDetailDestination>().id

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        _state.value = CharacterDetailsState(isLoading = true)
        viewModelScope.launch {
            delay(2000)
            val character = db.getCharacterById(characterId)
            if (character != null) {
                _state.value = CharacterDetailsState(
                    isLoading = false,
                    character = character
                )
            } else {
                _state.value = CharacterDetailsState(
                    isLoading = false,
                    hasError = true
                )
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

            CharacterDetailsEvent.onRetryClick -> {
                loadCharacter()
            }
        }
    }
}