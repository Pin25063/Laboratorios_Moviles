package plat.pinto_ramirez.lab7.character.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    // Insertar la lista inicial. Si un registro ya existe, lo ignora para no duplicar.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    // Insertar un solo personaje
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    // Actualizar personaje completo
    @Update
    suspend fun update(character: CharacterEntity)

    // Eliminar un personaje
    @Delete
    suspend fun delete(character: CharacterEntity)

    // Actualiza únicamente la columna isFavorite del personaje con el ID enviado
    @Query("UPDATE characters SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    // Borra todos los registros (útil para cuando cerremos sesión)
    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}
