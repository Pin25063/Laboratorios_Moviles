package plat.pinto_ramirez.lab7.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import Location
import plat.pinto_ramirez.lab7.RepositoryProvider
import plat.pinto_ramirez.lab7.location.data.LocationRepository

class LocationListViewModel @JvmOverloads constructor(
    private val repository: LocationRepository = RepositoryProvider.locationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LocationListState(isLoading = true))
    val state: StateFlow<LocationListState> = _state.asStateFlow()

    private var allLocations: List<Location> = emptyList()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                allLocations = repository.getLocations()
                applyFilter(_state.value.selectedFilter)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    private fun applyFilter(filter: LocationFilter) {
        val filtered = when (filter) {
            LocationFilter.ALL -> allLocations
            LocationFilter.FAVORITES -> allLocations.filter { it.isFavorite }
            LocationFilter.NON_FAVORITES -> allLocations.filter { !it.isFavorite }
        }
        _state.value = _state.value.copy(
            isLoading = false,
            hasError = false,
            locations = filtered,
            selectedFilter = filter
        )
    }

    fun onEvent(event: LocationListEvents) {
        when (event) {
            is LocationListEvents.onLocationClick -> {}
            LocationListEvents.onLoadingLayoutClick -> {
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasError = true
                )
            }
            LocationListEvents.onRetryClick -> {
                loadLocations()
            }
            is LocationListEvents.onFilterSelected -> {
                applyFilter(event.filter)
            }
            is LocationListEvents.onFavoriteClick -> {
                viewModelScope.launch {
                    repository.updateFavoriteStatus(event.locationId, !event.currentStatus)
                    allLocations = repository.getLocations()
                    applyFilter(_state.value.selectedFilter)
                }
            }
        }
    }
}
