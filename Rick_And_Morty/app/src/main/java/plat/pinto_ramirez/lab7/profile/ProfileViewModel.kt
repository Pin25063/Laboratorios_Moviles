package plat.pinto_ramirez.lab7.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.UserStore

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userStore = UserStore(application)

    val userName: StateFlow<String> = userStore.getUserName.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Cargando..."
    )

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            userStore.clearUserName()
            onLogoutSuccess()
        }
    }
}