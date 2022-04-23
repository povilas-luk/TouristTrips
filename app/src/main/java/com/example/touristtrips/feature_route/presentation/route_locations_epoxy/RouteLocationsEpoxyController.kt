package com.example.touristtrips.feature_route.presentation.route_locations_epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.touristtrips.feature_location.domain.model.Location

class RouteLocationsEpoxyController(
    val itemSelected: (String) -> Unit,
    val deleteItemSelected: (String) -> Unit
): EpoxyController() {
    var locationsList: List<Location> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        locationsList.forEach { location ->
            RouteLocationsEpoxyModel(location, itemSelected, deleteItemSelected).id(location.locationId).addTo(this)
        }
    }
}
