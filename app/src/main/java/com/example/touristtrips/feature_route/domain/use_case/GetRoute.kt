package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocationRepository
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.RouteRepository

class GetRoute(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(id: String): Route? {
        return repository.getRouteById(id)
    }
}