package plat.pinto_ramirez.lab7.character.data

import plat.pinto_ramirez.lab7.Character

interface CharacterRepository {
    suspend fun syncInitialData()
    suspend fun getCharacters(): List<Character>
    suspend fun getCharacterById(id: Int): Character?
    suspend fun insertCharacter(character: Character)
    suspend fun updateCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
}

class CharacterRepositoryImpl(
    private val api: CharacterApi,
    private val dao: CharacterDao
) : CharacterRepository {

    override suspend fun syncInitialData() {
        val dtos = api.getCharacters()
        val entities = dtos.map { it.toEntity() }
        dao.insertAll(entities)
    }

    override suspend fun getCharacters(): List<Character> {
        return dao.getAllCharacters().map { it.toModel() }
    }

    override suspend fun getCharacterById(id: Int): Character? {
        return dao.getCharacterById(id)?.toModel()
    }

    override suspend fun insertCharacter(character: Character) {
        dao.insert(character.toEntity())
    }

    override suspend fun updateCharacter(character: Character) {
        dao.update(character.toEntity())
    }

    override suspend fun deleteCharacter(character: Character) {
        dao.delete(character.toEntity())
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        dao.updateFavoriteStatus(id, isFavorite)
    }
}
