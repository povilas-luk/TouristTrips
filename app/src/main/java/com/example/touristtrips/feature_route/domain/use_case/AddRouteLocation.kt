package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_location.domain.model.InvalidLocationException
import com.example.touristtrips.feature_route.domain.model.InvalidRouteException
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.RouteRepository

class AddRouteLocation(
    private val repository: RouteRepository
) {

    suspend operator fun invoke(routeId: String, locationId: String) {
        repository.insertRouteWithLocation(routeId, locationId)
    }
}