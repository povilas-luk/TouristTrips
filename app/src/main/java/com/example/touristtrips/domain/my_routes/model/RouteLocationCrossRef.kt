package com.example.touristtrips.domain.my_routes.model

import androidx.room.Entity

@Entity(primaryKeys = ["routeId", "locationId"])
data class RouteLocationCrossRef(
    val routeId: String,
    val locationId: String
)
