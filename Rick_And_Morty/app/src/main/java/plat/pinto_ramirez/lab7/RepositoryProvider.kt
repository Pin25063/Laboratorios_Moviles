package plat.pinto_ramirez.lab7

import plat.pinto_ramirez.lab7.character.data.CharacterRepository
import plat.pinto_ramirez.lab7.character.data.CharacterRepositoryImpl
import plat.pinto_ramirez.lab7.character.data.KtorCharacterApi
import plat.pinto_ramirez.lab7.location.data.KtorLocationApi
import plat.pinto_ramirez.lab7.location.data.LocationRepository
import plat.pinto_ramirez.lab7.location.data.LocationRepositoryImpl
import plat.pinto_ramirez.lab7.network.HttpClientFactory

object RepositoryProvider {
    private val client by lazy { HttpClientFactory.create() }

    val characterRepository: CharacterRepository by lazy {
        CharacterRepositoryImpl(
            api = KtorCharacterApi(client),
            dao = DatabaseProvider.db.characterDao()
        )
    }

    val locationRepository: LocationRepository by lazy {
        LocationRepositoryImpl(
            api = KtorLocationApi(client),
            dao = DatabaseProvider.db.locationDao()
        )
    }
}
