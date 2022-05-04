package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.data.room.repository.LocalRouteRepositoryImpl
import com.example.touristtrips.domain.my_routes.model.Route
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository
import kotlinx.coroutines.flow.Flow

class GetRoutes(
    private val repositoryLocal: LocalRouteRepository
) {
    operator fun invoke(): Flow<List<Route>> {
        return repositoryLocal.getRoutes()
    }
}