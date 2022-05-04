package com.example.touristtrips.domain.my_locations.use_case

import com.example.touristtrips.data.room.repository.LocalLocationRepositoryImpl
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_locations.repository.LocalLocationRepository

class GetLocation (
    private val repositoryLocal: LocalLocationRepository
) {
    suspend operator fun invoke(id: String): Location? {
        return repositoryLocal.getLocationById(id)
    }
}