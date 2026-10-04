package plat.pinto_ramirez.lab7.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.CharacterDb

class CharacterListViewModel : ViewModel(){
    val db = CharacterDb()
    private val _state = MutableStateFlow(CharacterListState(isLoading = true))
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters(){
        _state.value = CharacterListState(isLoading = true)
        viewModelScope.launch {
            delay(4000)
            val characters = db.getAllCharacters()
            _state.value = CharacterListState(
                isLoading = false,
                characterList = characters
            )
        }
    }

    fun OnEvent(event: CharacterListEvent){
        when (event){
            is CharacterListEvent.onCharacterClick -> {

            }

            CharacterListEvent.onLoadingLayoutClick -> {
                _state.value = CharacterListState(
                    isLoading = false,
                    hasError = true
                )
            }

            CharacterListEvent.onRetryClick -> {
                loadCharacters()
            }
        }
    }
}