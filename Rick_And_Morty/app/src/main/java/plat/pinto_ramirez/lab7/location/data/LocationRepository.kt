package plat.pinto_ramirez.lab7.location.data

import Location

interface LocationRepository {
    suspend fun syncInitialData()
    suspend fun getLocations(): List<Location>
    suspend fun getLocationById(id: Int): Location?
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
}

class LocationRepositoryImpl(
    private val api: LocationApi,
    private val dao: LocationDao
) : LocationRepository {

    override suspend fun syncInitialData() {
        val dtos = api.getLocations()
        val entities = dtos.map { it.toEntity() }
        dao.insertAll(entities)
    }

    override suspend fun getLocations(): List<Location> {
        return dao.getAllLocations().map { it.toModel() }
    }

    override suspend fun getLocationById(id: Int): Location? {
        return dao.getLocationById(id)?.toModel()
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        dao.updateFavoriteStatus(id, isFavorite)
    }
}
