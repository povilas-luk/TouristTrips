package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.my_routes.model.Route
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class DeleteRoute(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(route: Route) {
        repositoryLocal.deleteRoute(route)
    }
}