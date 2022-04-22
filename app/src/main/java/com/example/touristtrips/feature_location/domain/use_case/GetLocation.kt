package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocalLocationRepository

class GetLocation (
    private val repositoryLocal: LocalLocationRepository
) {
    suspend operator fun invoke(id: String): Location? {
        return repositoryLocal.getLocationById(id)
    }
}