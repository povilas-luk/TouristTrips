package com.example.touristtrips.core.data.local_data.local_data_source

import androidx.room.Entity

@Entity(primaryKeys = ["routeId", "locationId"])
data class RouteLocationCrossRef(
    val routeId: String,
    val locationId: String
)
