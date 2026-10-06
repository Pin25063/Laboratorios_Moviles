package plat.pinto_ramirez.lab7.location.list


sealed interface LocationListEvents{
    data class onLocationClick (val locationId: Int) : LocationListEvents
    data object onLoadingLayoutClick : LocationListEvents
    data object onRetryClick : LocationListEvents
}