package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class UpdateRouteLocation(
    private val repositoryLocal: LocalRouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String, newLocationSeq: Int) {
        val routeLocationsSeq = repositoryLocal.getRouteLocationsSeq(routeId)
        if (routeLocationsSeq != null) {
            val oldLocationSeq = routeLocationsSeq.indexOf(locationId)
            if (oldLocationSeq > newLocationSeq) {
                for (i in newLocationSeq..oldLocationSeq) {
                    repositoryLocal.updateRouteWithLocation(routeId, routeLocationsSeq[i], i + 1)
                }
            } else if (oldLocationSeq < newLocationSeq) {
                for (i in oldLocationSeq..newLocationSeq) {
                    repositoryLocal.updateRouteWithLocation(routeId, routeLocationsSeq[i], i - 1)
                }
            }
            repositoryLocal.updateRouteWithLocation(routeId, locationId, newLocationSeq)
        }
    }
}