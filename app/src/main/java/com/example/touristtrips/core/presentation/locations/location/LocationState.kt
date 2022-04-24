package com.example.touristtrips.core.presentation.locations.location

import com.example.touristtrips.core.domain.util.SortOrder
import com.example.touristtrips.core.domain.util.SortType
import com.example.touristtrips.feature_location.domain.model.Location

data class LocationState(
    var locations: ArrayList<Location> = ArrayList(),
    var location: Location = Location(),
    var sortOrder: SortOrder? = null
)
