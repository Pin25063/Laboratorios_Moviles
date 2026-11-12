package plat.pinto_ramirez.lab7.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.RepositoryProvider
import plat.pinto_ramirez.lab7.UserStore
import plat.pinto_ramirez.lab7.character.data.CharacterRepository
import plat.pinto_ramirez.lab7.location.data.LocationRepository

class LoginViewModel @JvmOverloads constructor(
    application: Application,
    private val characterRepository: CharacterRepository = RepositoryProvider.characterRepository,
    private val locationRepository: LocationRepository = RepositoryProvider.locationRepository
) : AndroidViewModel(application) {
    private val userStore = UserStore(application)

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _navigateToCharacters = MutableStateFlow(false)
    val navigateToCharacters: StateFlow<Boolean> = _navigateToCharacters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val savedName = userStore.getUserName.first()
            if (savedName.isNotEmpty()) {
                syncDataAndNavigate(savedName, isRestoringSession = true)
            }
        }
    }

    fun onNameChange(newName: String) {
        _userName.value = newName
    }

    fun login() {
        if (_userName.value.isNotBlank()) {
            syncDataAndNavigate(_userName.value, isRestoringSession = false)
        }
    }

    private fun syncDataAndNavigate(name: String, isRestoringSession: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                characterRepository.syncInitialData()
                locationRepository.syncInitialData()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            delay(2000)

            if (!isRestoringSession) {
                userStore.saveUserName(name)
            }

            _isLoading.value = false
            _navigateToCharacters.value = true
        }
    }

    fun onNavigationDone() {
        _navigateToCharacters.value = false
    }
}
