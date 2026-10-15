package plat.pinto_ramirez.lab7.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.UserStore

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userStore = UserStore(application)

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _navigateToCharacters = MutableStateFlow(false)
    val navigateToCharacters: StateFlow<Boolean> = _navigateToCharacters.asStateFlow()

    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val savedName = userStore.getUserName.first()
            if (savedName.isNotEmpty()) {
                _navigateToCharacters.value = true
            }
        }
    }

    fun onNameChange(newName: String) {
        _userName.value = newName
    }

    fun login() {
        viewModelScope.launch {
            if (_userName.value.isNotBlank()) {
                userStore.saveUserName(_userName.value)
                _navigateToCharacters.value = true
            }
        }
    }

    fun onNavigationDone() {
        _navigateToCharacters.value = false
    }
}