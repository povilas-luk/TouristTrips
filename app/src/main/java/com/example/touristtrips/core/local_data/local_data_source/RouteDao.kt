package com.example.touristtrips.core.local_data.local_data_source

import androidx.room.*
import com.example.touristtrips.feature_route.domain.model.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {

    @Query("SELECT * FROM route_entity")
    fun getAllRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM route_entity WHERE routeId = :id")
    suspend fun getRouteById(id: String) : Route?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)

    @Update
    suspend fun updateRoute(route: Route)

    @Transaction
    @Query("SELECT * FROM route_entity")
    fun getRoutesWithLocations(): Flow<List<RouteWithLocations>>

    @Transaction
    @Query("SELECT * FROM route_entity WHERE routeId = :id")
    fun getRouteWithLocationsById(id: String): RouteWithLocations?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRouteWithLocation(crossRef: RouteLocationCrossRef)

    @Update
    suspend fun updateRouteWithLocation(crossRef: RouteLocationCrossRef)

    @Delete
    suspend fun deleteRouteWithLocation(crossRef: RouteLocationCrossRef)
}