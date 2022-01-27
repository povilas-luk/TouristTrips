package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.RouteRepository

class UpdateRoute(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(route: Route) {
        repository.updateRoute(route)
    }
}