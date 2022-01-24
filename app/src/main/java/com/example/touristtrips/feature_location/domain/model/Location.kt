package com.example.touristtrips.feature_location.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey val id: String = "",
    val type: String = "",
    val name: String = "",
    val description: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val city: String = "",
    val createdAt: Long = 0L,
    val imageUrl: String = "",
    val months_to_visit: String = "",
    val price: Float = 0.0F
)
