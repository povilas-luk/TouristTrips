package com.example.touristtrips.feature_route.presentation.routes_epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.touristtrips.feature_route.presentation.all_routes_list.RoutesState

class RoutesEpoxyController(
    val itemSelected: (String) -> Unit
): EpoxyController() {
    var routesState: RoutesState = RoutesState()
    set(value) {
        field = value
        requestModelBuild()
    }

    override fun buildModels() {
        routesState.routes.forEach { route ->
            RoutesEpoxyModel(route, itemSelected).id(route.routeId).addTo(this)
        }
    }
}