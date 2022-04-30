package com.example.touristtrips.domain.my_locations.repository

import com.example.touristtrips.domain.my_locations.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalLocationRepository {

    fun getLocations(): Flow<List<Location>>

    suspend fun getLocationById(id: String): Location?

    suspend fun insertLocation(location: Location)

    suspend fun deleteLocation(location: Location)

    suspend fun updateLocation(location: Location)
}