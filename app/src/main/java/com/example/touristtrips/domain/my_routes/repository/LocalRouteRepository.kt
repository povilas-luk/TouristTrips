package com.example.touristtrips.domain.my_routes.repository

import com.example.touristtrips.domain.my_routes.model.RouteWithLocations
import com.example.touristtrips.domain.my_routes.model.Route
import kotlinx.coroutines.flow.Flow

interface LocalRouteRepository {

    fun getRoutes(): Flow<List<Route>>

    fun getRoutesWithLocations(): Flow<List<RouteWithLocations>>

    suspend fun getRouteById(id: String): Route?

    suspend fun getRouteWithLocationsById(id: String): RouteWithLocations?

    suspend fun insertRoute(route: Route)

    suspend fun updateRoute(route: Route)

    suspend fun deleteRoute(route: Route)

    suspend fun insertRouteWithLocation(routeId: String, locationId: String)

    suspend fun updateRouteWithLocation(routeId: String, locationId: String)

    suspend fun deleteRouteWithLocation(routeId: String, locationId: String)
}