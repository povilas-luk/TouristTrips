package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.model.RouteWithLocations
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository
import kotlinx.coroutines.flow.Flow

class GetRoutesWithLocations(
    private val repositoryLocal: LocalRouteRepository
) {
    operator fun invoke(): Flow<List<RouteWithLocations>> {
        return repositoryLocal.getRoutesWithLocations()
    }
}