package com.example.touristtrips.feature_location.presentation.locations

import com.example.touristtrips.feature_location.domain.model.Location

data class LocationState(
    var locations: ArrayList<Location> = ArrayList(),
    var location: Location = Location()
)
