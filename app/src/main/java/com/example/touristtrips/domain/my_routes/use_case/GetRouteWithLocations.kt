package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.model.RouteWithLocations
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class GetRouteWithLocations(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(id: String): RouteWithLocations? {
        val locationsSeq = repositoryLocal.getRouteLocationsSeq(id)
        val routeWithLocations = repositoryLocal.getRouteWithLocationsById(id)

        return if (locationsSeq != null && routeWithLocations != null) {
            val orderBy = locationsSeq.withIndex().associate { it.value to it.index }
            RouteWithLocations(
                routeWithLocations.route,
                routeWithLocations.locations.sortedBy { orderBy[it.locationId] })
        } else {
            null
        }

    }
}