package plat.pinto_ramirez.lab7

import androidx.room.Database
import androidx.room.RoomDatabase
import plat.pinto_ramirez.lab7.character.data.CharacterDao
import plat.pinto_ramirez.lab7.character.data.CharacterEntity
import plat.pinto_ramirez.lab7.location.data.LocationDao
import plat.pinto_ramirez.lab7.location.data.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
}