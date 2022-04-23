package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.core.data.local_data.local_data_source.RouteWithLocations
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository
import kotlinx.coroutines.flow.Flow

class GetRoutesWithLocations(
    private val repositoryLocal: LocalRouteRepository
) {
    operator fun invoke(): Flow<List<RouteWithLocations>> {
        return repositoryLocal.getRoutesWithLocations()
    }
}