package plat.pinto_ramirez.lab7.location.list

enum class LocationFilter {
    ALL,
    FAVORITES,
    NON_FAVORITES
}

sealed interface LocationListEvents {
    data class onLocationClick(val locationId: Int) : LocationListEvents
    data object onLoadingLayoutClick : LocationListEvents
    data object onRetryClick : LocationListEvents
    data class onFilterSelected(val filter: LocationFilter) : LocationListEvents
    data class onFavoriteClick(val locationId: Int, val currentStatus: Boolean) : LocationListEvents
}
