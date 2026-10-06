package plat.pinto_ramirez.lab7.location.details

import LocationDb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.AppNavHost.LocationDetailDestination

class LocationDetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val db = LocationDb()
    private val _state = MutableStateFlow(LocationDetailsState(isLoading = true))
    val state: StateFlow<LocationDetailsState> = _state.asStateFlow()

    private val locationId: Int = savedStateHandle.toRoute<LocationDetailDestination>().id

    init {
        loadLocation()
    }

    fun loadLocation() {
        _state.value = LocationDetailsState(isLoading = true)
        viewModelScope.launch {
            delay(2000)
            val location = db.getLocationById(locationId)
            if (location != null) {
                _state.value = LocationDetailsState(
                    isLoading = false,
                    location = location
                )
            } else {
                _state.value = LocationDetailsState(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    fun onEvent(event: LocationDetailsEvent) {
        when (event) {
            is LocationDetailsEvent.onLoadingLayoutClick -> {
                _state.value = LocationDetailsState(
                    isLoading = false,
                    hasError = true
                )
            }

            LocationDetailsEvent.onRetryClick -> {
                loadLocation()
            }
        }
    }
}