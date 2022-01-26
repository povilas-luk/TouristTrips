package com.example.touristtrips.feature_location.presentation.location_epoxy_model

import com.airbnb.epoxy.EpoxyController
import com.example.touristtrips.feature_location.presentation.locations.LocationState

class LocationsEpoxyController(
    val itemSelected: (String) -> Unit
): EpoxyController() {
    var locationsState: LocationState = LocationState()
    set(value) {
        field = value
        requestModelBuild()
    }

    override fun buildModels() {
        locationsState.locations.forEach { location ->
            LocationEpoxyModel(location, itemSelected).id(location.locationId).addTo(this)
        }
    }
}