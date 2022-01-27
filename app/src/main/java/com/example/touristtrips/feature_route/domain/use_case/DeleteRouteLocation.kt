package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.repository.RouteRepository

class DeleteRouteLocation(
    private val repository: RouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String) {
        repository.deleteRouteWithLocation(routeId, locationId)
    }
}