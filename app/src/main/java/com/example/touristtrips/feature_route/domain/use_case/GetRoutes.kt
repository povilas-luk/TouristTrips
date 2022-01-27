package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocationRepository
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow

class GetRoutes(
    private val repository: RouteRepository
) {
    operator fun invoke(): Flow<List<Route>> {
        return repository.getRoutes()
    }
}