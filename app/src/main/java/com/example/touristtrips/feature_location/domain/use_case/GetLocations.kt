package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocations(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<List<Location>> {
        return repository.getLocations()
    }
}