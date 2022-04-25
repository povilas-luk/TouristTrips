package com.example.touristtrips.core.presentation.epoxy.location_epoxy_model

import android.text.TextWatcher
import com.airbnb.epoxy.EpoxyController
import com.example.touristtrips.core.presentation.epoxy.model.SearchHeaderEpoxyModel
import com.example.touristtrips.core.presentation.locations.location.LocationState

class LocationsEpoxyController(
    val itemSelected: (String) -> Unit,
    private val textWatcher: TextWatcher
): EpoxyController() {
    var locationsState: LocationState = LocationState()
    set(value) {
        field = value
        requestModelBuild()
    }

    override fun buildModels() {
        SearchHeaderEpoxyModel(textWatcher).id("search").addTo(this)

        locationsState.locations.forEach { location ->
            LocationEpoxyModel(location, itemSelected).id(location.locationId).addTo(this)
        }
    }
}