package com.example.touristtrips.domain.my_locations.use_case

import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_locations.repository.LocalLocationRepository

class DeleteLocation(
    private val repositoryLocal: LocalLocationRepository
) {
    suspend operator fun invoke(location: Location) {
        repositoryLocal.deleteLocation(location)
    }
}