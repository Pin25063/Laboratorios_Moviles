package plat.pinto_ramirez.lab7.character.data

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponseDTO(
    val results: List<CharacterDTO>
)

@Serializable
data class CharacterDTO(
    val id: Int,
    val name: String,
    val status: String = "",
    val species: String = "",
    val gender: String = "",
    val image: String = ""
)

fun CharacterDTO.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
        isFavorite = false
    )
}
