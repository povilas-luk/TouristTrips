package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class AddRouteLocation(
    private val repositoryLocal: LocalRouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String) {
        repositoryLocal.insertRouteWithLocation(routeId, locationId)
    }
}