package plat.pinto_ramirez.lab7.location.list

import Location

data class LocationListState (
    val locations: List<Location> = emptyList(),
    val isLoading: Boolean = false,
    val hasError : Boolean = false
)
