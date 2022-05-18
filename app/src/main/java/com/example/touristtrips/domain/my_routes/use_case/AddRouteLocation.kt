package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class AddRouteLocation(
    private val repositoryLocal: LocalRouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String, locationSeq: Int?) {
        if (locationSeq != null) {
            repositoryLocal.insertRouteWithLocation(routeId, locationId, locationSeq)
        } else {
            val newLocationSeq = repositoryLocal.getRouteLocationsSeq(routeId)?.size
            repositoryLocal.insertRouteWithLocation(routeId, locationId, newLocationSeq ?: 0)
        }
    }
}