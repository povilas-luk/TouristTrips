package com.example.touristtrips.domain.my_locations.use_case

import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_locations.repository.LocalLocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocations(
    private val repositoryLocal: LocalLocationRepository
) {
    operator fun invoke(): Flow<List<Location>> {
        return repositoryLocal.getLocations()
    }
}