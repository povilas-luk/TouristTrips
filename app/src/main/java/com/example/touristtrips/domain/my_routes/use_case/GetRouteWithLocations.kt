package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.model.RouteWithLocations
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class GetRouteWithLocations(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(id: String): RouteWithLocations? {
        return repositoryLocal.getRouteWithLocationsById(id)
    }
}