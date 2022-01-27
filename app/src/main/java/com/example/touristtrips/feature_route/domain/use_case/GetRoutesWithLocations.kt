package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.core.local_data.local_data_source.RouteWithLocations
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow

class GetRoutesWithLocations(
    private val repository: RouteRepository
) {
    operator fun invoke(): Flow<List<RouteWithLocations>> {
        return repository.getRoutesWithLocations()
    }
}