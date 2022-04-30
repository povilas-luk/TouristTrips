package com.example.touristtrips.domain.shared.util.location

import com.example.touristtrips.domain.my_locations.model.Location

data class LocationsResponse(
    val locations: List<Location> = listOf()
)