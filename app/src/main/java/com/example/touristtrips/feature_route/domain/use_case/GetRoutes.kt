package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository
import kotlinx.coroutines.flow.Flow

class GetRoutes(
    private val repositoryLocal: LocalRouteRepository
) {
    operator fun invoke(): Flow<List<Route>> {
        return repositoryLocal.getRoutes()
    }
}