package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.core.local_data.local_data_source.RouteWithLocations
import com.example.touristtrips.feature_route.domain.repository.RouteRepository

class GetRouteWithLocations(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(id: String): RouteWithLocations? {
        return repository.getRouteWithLocationsById(id)
    }
}