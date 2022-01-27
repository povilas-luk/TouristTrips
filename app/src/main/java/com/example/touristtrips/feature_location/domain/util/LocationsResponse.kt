package com.example.touristtrips.feature_location.domain.util

import com.example.touristtrips.feature_location.domain.model.Location

data class LocationsResponse(
    val locations: List<Location> = listOf()
)