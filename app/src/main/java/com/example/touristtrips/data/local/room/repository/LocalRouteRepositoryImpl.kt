package com.example.touristtrips.data.local.room.repository

import com.example.touristtrips.data.local.room.source.RouteDao
import com.example.touristtrips.domain.shared.model.route.RouteLocationCrossRef
import com.example.touristtrips.domain.shared.model.route.RouteWithLocations
import com.example.touristtrips.domain.shared.model.route.Route
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository
import kotlinx.coroutines.flow.Flow

class LocalRouteRepositoryImpl(
    private val dao: RouteDao
) : LocalRouteRepository {
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

    override suspend fun insertRouteWithLocation(routeId: String, locationId: String, locationSeq: Int) {
        val crossRef = RouteLocationCrossRef(routeId, locationId, locationSeq)
        return dao.insertRouteWithLocation(crossRef)
    }

    override suspend fun updateRouteWithLocation(routeId: String, locationId: String, locationSeq: Int) {
        return dao.updateRouteWithLocation(routeId, locationId, locationSeq)
    }

    override suspend fun deleteRouteWithLocation(routeId: String, locationId: String) {
        return dao.deleteRouteWithLocation(routeId, locationId)
    }

    override suspend fun getRouteLocationsSeq(routeId: String): List<String>? {
        return dao.getRouteLocationsSeq(routeId)
    }

}