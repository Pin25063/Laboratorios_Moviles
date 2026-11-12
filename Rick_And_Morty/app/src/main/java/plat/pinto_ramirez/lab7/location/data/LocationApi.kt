package plat.pinto_ramirez.lab7.location.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface LocationApi {
    suspend fun getLocations(): List<LocationDTO>
}

class KtorLocationApi(
    private val client: HttpClient
) : LocationApi {
    override suspend fun getLocations(): List<LocationDTO> {
        val response = client.get("location").body<LocationResponseDTO>()
        return response.results
    }
}
