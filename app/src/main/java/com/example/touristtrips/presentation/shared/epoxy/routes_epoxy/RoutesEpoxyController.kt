package com.example.touristtrips.presentation.shared.epoxy.routes_epoxy

import android.text.TextWatcher
import com.airbnb.epoxy.EpoxyController
import com.example.touristtrips.presentation.shared.epoxy.model.SearchHeaderEpoxyModel
import com.example.touristtrips.presentation.shared.routes.route.RoutesState

class RoutesEpoxyController(
    val itemSelected: (String) -> Unit,
    private val textWatcher: TextWatcher
) : EpoxyController() {
    var routesState: RoutesState = RoutesState()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        SearchHeaderEpoxyModel(textWatcher).id("search").addTo(this)

        routesState.routes.forEach { route ->
            RoutesEpoxyModel(route, itemSelected).id(route.routeId).addTo(this)
        }
    }
}