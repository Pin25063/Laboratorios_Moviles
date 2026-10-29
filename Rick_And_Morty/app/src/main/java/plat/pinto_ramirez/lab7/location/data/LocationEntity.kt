package plat.pinto_ramirez.lab7.location.data

import Location
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

fun LocationEntity.toModel(): Location {
    return Location(id, name, type, dimension)
}

fun Location.toEntity(): LocationEntity {
    return LocationEntity(id, name, type, dimension)
}