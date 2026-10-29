package plat.pinto_ramirez.lab7.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import plat.pinto_ramirez.lab7.DatabaseProvider
import plat.pinto_ramirez.lab7.location.data.toModel

class LocationListViewModel : ViewModel() {

    private val db = DatabaseProvider.db

    private val _state = MutableStateFlow(LocationListState(isLoading = true))
    val state: StateFlow<LocationListState> = _state.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.value = LocationListState(isLoading = true)
        viewModelScope.launch {
            val entities = db.locationDao().getAllLocations()

            val models = entities.map { it.toModel() }

            _state.value = LocationListState(
                isLoading = false,
                locations = models
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