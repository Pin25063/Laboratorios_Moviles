package plat.pinto_ramirez.lab7.character.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface CharacterApi {
    suspend fun getCharacters(): List<CharacterDTO>
}

class KtorCharacterApi(
    private val client: HttpClient
) : CharacterApi {
    override suspend fun getCharacters(): List<CharacterDTO> {
        val response = client.get("character").body<CharacterResponseDTO>()
        return response.results
    }
}
