package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class AddRouteLocation(
    private val repositoryLocal: LocalRouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String) {
        repositoryLocal.insertRouteWithLocation(routeId, locationId)
    }
}