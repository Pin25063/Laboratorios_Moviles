package plat.pinto_ramirez.lab7.character.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import plat.pinto_ramirez.lab7.Character

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val isFavorite: Boolean
)

fun CharacterEntity.toModel(): Character {
    return Character(id, name, status, species, gender, image, isFavorite)
}

fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(id, name, status, species, gender, image, isFavorite)
}