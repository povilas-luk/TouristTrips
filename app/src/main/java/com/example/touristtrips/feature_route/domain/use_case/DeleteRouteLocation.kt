package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class DeleteRouteLocation(
    private val repositoryLocal: LocalRouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String) {
        repositoryLocal.deleteRouteWithLocation(routeId, locationId)
    }
}