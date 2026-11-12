package plat.pinto_ramirez.lab7.location.data

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseDTO(
    val results: List<LocationDTO>
)

@Serializable
data class LocationDTO(
    val id: Int,
    val name: String,
    val type: String = "",
    val dimension: String = ""
)

fun LocationDTO.toEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        isFavorite = false
    )
}
