package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class DeleteRouteLocation(
    private val repositoryLocal: LocalRouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String) {
        repositoryLocal.deleteRouteWithLocation(routeId, locationId)
        repositoryLocal.getRouteLocationsSeq(routeId)?.forEachIndexed { index, locationSeqId ->
            repositoryLocal.updateRouteWithLocation(routeId, locationSeqId, index)
        }
    }
}