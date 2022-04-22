package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class GetRoute(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(id: String): Route? {
        return repositoryLocal.getRouteById(id)
    }
}