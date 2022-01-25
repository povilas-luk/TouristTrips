package com.example.touristtrips.feature_location.presentation.locations

import com.example.touristtrips.feature_location.domain.model.Location

data class LocationState(
    val locations: List<Location> = emptyList()
)
