package plat.pinto_ramirez.lab7.location.details

sealed interface LocationDetailsEvent{
    data object onLoadingLayoutClick : LocationDetailsEvent
    data object onRetryClick : LocationDetailsEvent
}