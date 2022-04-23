package com.example.touristtrips.core.data.local_data.local_repository

import com.example.touristtrips.core.data.local_data.local_data_source.RouteDao
import com.example.touristtrips.core.data.local_data.local_data_source.RouteLocationCrossRef
import com.example.touristtrips.core.data.local_data.local_data_source.RouteWithLocations
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository
import kotlinx.coroutines.flow.Flow

class LocalRouteRepositoryImpl(
    private val dao: RouteDao
): LocalRouteRepository {
    override fun getRoutes(): Flow<List<Route>> {
        return dao.getAllRoutes()
    }

    override fun getRoutesWithLocations(): Flow<List<RouteWithLocations>> {
        return dao.getRoutesWithLocations()
    }

    override suspend fun getRouteById(id: String): Route? {
        return dao.getRouteById(id)
    }

    override suspend fun getRouteWithLocationsById(id: String): RouteWithLocations? {
        return dao.getRouteWithLocationsById(id)
    }

    override suspend fun insertRoute(route: Route) {
        return dao.insertRoute(route)
    }

    override suspend fun updateRoute(route: Route) {
        return dao.updateRoute(route)
    }

    override suspend fun deleteRoute(route: Route) {
        return dao.deleteRoute(route)
    }

    override suspend fun insertRouteWithLocation(routeId: String, locationId: String) {
        val crossRef = RouteLocationCrossRef(routeId, locationId)
        return dao.insertRouteWithLocation(crossRef)
    }

    override suspend fun updateRouteWithLocation(routeId: String, locationId: String) {
        val crossRef = RouteLocationCrossRef(routeId, locationId)
        return dao.updateRouteWithLocation(crossRef)
    }

    override suspend fun deleteRouteWithLocation(routeId: String, locationId: String) {
        val crossRef = RouteLocationCrossRef(routeId, locationId)
        return dao.deleteRouteWithLocation(crossRef)
    }

}