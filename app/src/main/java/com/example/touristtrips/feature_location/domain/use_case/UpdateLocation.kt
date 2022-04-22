package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocalLocationRepository

class UpdateLocation(
    private val repositoryLocal: LocalLocationRepository
) {
    suspend operator fun invoke(location: Location) {
        repositoryLocal.updateLocation(location)
    }
}