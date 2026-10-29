package plat.pinto_ramirez.lab7.location.list

import plat.pinto_ramirez.lab7.location.data.LocationDb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationListViewModel : ViewModel() {
    val db = LocationDb()
    private val _state = MutableStateFlow(LocationListState(isLoading = true))
    val state: StateFlow<LocationListState> = _state.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.value = LocationListState(isLoading = true)
        viewModelScope.launch {
            delay(4000)
            val locations = db.getAllLocations()
            _state.value = LocationListState(
                isLoading = false,
                locations = locations
            )
        }
    }

    fun onEvent(event: LocationListEvents) {
        when (event) {
            is LocationListEvents.onLocationClick -> {
            }
            LocationListEvents.onLoadingLayoutClick -> {
                _state.value = LocationListState(
                    isLoading = false,
                    hasError = true
                )
            }
            LocationListEvents.onRetryClick -> {
                loadLocations()
            }
        }
    }
}

