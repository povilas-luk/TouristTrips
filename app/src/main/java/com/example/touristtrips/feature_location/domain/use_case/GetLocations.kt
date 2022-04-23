package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocalLocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocations(
    private val repositoryLocal: LocalLocationRepository
) {
    operator fun invoke(): Flow<List<Location>> {
        return repositoryLocal.getLocations()
    }
}