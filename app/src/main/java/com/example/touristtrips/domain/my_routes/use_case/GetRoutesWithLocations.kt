package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.shared.model.route.RouteWithLocations
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository
import kotlinx.coroutines.flow.Flow

class GetRoutesWithLocations(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(): Flow<List<RouteWithLocations>> {
        return repositoryLocal.getRoutesWithLocations()
    }
}