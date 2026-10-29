package plat.pinto_ramirez.lab7.character.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    // Insertar la lista inicial. Si un registro ya existe, lo ignora para no duplicar.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    // Actualiza únicamente la columna isFavorite del personaje con el ID enviado
    @Query("UPDATE characters SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    // Borra todos los registros (útil para cuando cerremos sesión)
    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}