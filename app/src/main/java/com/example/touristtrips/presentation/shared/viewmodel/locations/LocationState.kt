package com.example.touristtrips.presentation.shared.viewmodel.locations

import com.example.touristtrips.domain.shared.model.SortOrder
import com.example.touristtrips.domain.my_locations.model.Location

data class LocationState(
    var locations: ArrayList<Location> = ArrayList(),
    var location: Location = Location(),
    var sortOrder: SortOrder? = null
)
