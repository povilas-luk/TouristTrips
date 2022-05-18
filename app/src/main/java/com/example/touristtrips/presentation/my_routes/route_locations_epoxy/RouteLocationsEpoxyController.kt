package com.example.touristtrips.presentation.my_routes.route_locations_epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.touristtrips.domain.my_locations.model.Location

class RouteLocationsEpoxyController(
    val itemSelected: (String) -> Unit,
    val deleteItemSelected: (String) -> Unit,
    val sequenceItemSelected: (String) -> Unit,
    val deleteButtonIsActive: Boolean = true
) : EpoxyController() {
    var locationsList: List<Location> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        locationsList.forEach { location ->
            RouteLocationsEpoxyModel(
                location,
                itemSelected,
                deleteItemSelected,
                sequenceItemSelected,
                deleteButtonIsActive,
            ).id(location.locationId).addTo(this)
        }
    }
}
