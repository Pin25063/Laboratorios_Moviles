package plat.pinto_ramirez.lab7.location.details

import Location

data class LocationDetailsState (
        val location: Location? = null,
        val isLoading: Boolean = false,
        val hasError: Boolean = false
)