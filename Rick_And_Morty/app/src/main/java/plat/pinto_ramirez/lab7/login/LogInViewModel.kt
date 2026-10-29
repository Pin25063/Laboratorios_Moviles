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
import plat.pinto_ramirez.lab7.character.data.CharacterDb
import plat.pinto_ramirez.lab7.DatabaseProvider
import plat.pinto_ramirez.lab7.UserStore
import plat.pinto_ramirez.lab7.character.data.toEntity
import plat.pinto_ramirez.lab7.location.data.LocationDb
import plat.pinto_ramirez.lab7.location.data.toEntity

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userStore = UserStore(application)
    private val db = DatabaseProvider.db // Instancia de tu base de datos Room

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

            delay(4000)

            val existingCharacters = db.characterDao().getAllCharacters()

            if (existingCharacters.isEmpty()) {
                val initialCharacters = CharacterDb().getAllCharacters().map { it.toEntity() }
                val initialLocations = LocationDb().getAllLocations().map { it.toEntity() }

                db.characterDao().insertAll(initialCharacters)
                db.locationDao().insertAll(initialLocations)
            }

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