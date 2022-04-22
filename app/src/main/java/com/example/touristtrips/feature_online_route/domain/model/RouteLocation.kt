package com.example.touristtrips.feature_online_route.domain.model

import androidx.room.PrimaryKey

data class RouteLocation(
    val routeId: String = "",
    val type: String = "",
    val title: String = "",
    val description: String = "",
    val city: String = "",
    val createdAt: Long = 0L,
    val imageUrl: String = "",
    val months_to_visit: String = "",
    val price: Float = 0.0F,
    val locationsId: List<String>
)
