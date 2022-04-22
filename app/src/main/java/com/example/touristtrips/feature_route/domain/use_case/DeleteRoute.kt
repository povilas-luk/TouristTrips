package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class DeleteRoute(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(route: Route) {
        repositoryLocal.deleteRoute(route)
    }
}