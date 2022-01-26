package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocation (
    private val repository: LocationRepository
) {
    suspend operator fun invoke(id: String): Location? {
        return repository.getLocationById(id)
    }
}