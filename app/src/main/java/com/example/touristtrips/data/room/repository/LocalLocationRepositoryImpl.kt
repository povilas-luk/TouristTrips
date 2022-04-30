package com.example.touristtrips.data.room.repository

import com.example.touristtrips.data.room.source.LocationDao
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_locations.repository.LocalLocationRepository
import kotlinx.coroutines.flow.Flow

class LocalLocationRepositoryImpl(
    private val dao: LocationDao
) : LocalLocationRepository {
    override fun getLocations(): Flow<List<Location>> {
        return dao.getAllLocations()
    }

    override suspend fun getLocationById(id: String): Location? {
        return dao.getLocationById(id)
    }

    override suspend fun insertLocation(location: Location) {
        return dao.insertLocation(location)
    }

    override suspend fun deleteLocation(location: Location) {
        return dao.deleteLocation(location)
    }

    override suspend fun updateLocation(location: Location) {
        return dao.updateLocation(location)
    }

}