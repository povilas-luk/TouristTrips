package com.example.touristtrips.domain.my_routes.model

import androidx.room.Entity

@Entity(primaryKeys = ["routeId", "locationId"], tableName = "route_location")
data class RouteLocationCrossRef(
    val routeId: String,
    val locationId: String,
    val locationSequence: Int
)
