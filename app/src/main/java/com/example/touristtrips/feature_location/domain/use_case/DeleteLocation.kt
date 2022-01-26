package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocationRepository

class DeleteLocation(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(location: Location) {
        repository.deleteLocation(location)
    }
}