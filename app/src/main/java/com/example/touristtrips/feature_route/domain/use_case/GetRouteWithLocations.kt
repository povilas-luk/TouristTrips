package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.core.local_data.local_data_source.RouteWithLocations
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class GetRouteWithLocations(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(id: String): RouteWithLocations? {
        return repositoryLocal.getRouteWithLocationsById(id)
    }
}