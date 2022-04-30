package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.model.Route
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class GetRoute(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(id: String): Route? {
        return repositoryLocal.getRouteById(id)
    }
}