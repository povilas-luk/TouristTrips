package com.example.touristtrips.feature_location.domain.repository

import com.example.touristtrips.feature_location.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalLocationRepository {

    fun getLocations(): Flow<List<Location>>

    suspend fun getLocationById(id: String): Location?

    suspend fun insertLocation(location: Location)

    suspend fun deleteLocation(location: Location)

    suspend fun updateLocation(location: Location)
}