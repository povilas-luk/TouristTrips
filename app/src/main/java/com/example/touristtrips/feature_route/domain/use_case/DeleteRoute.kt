package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.RouteRepository

class DeleteRoute(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(route: Route) {
        repository.deleteRoute(route)
    }
}