package com.example.touristtrips.presentation.shared.viewmodel

import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.domain.my_routes.model.Route

data class RoutesState(
    var routes: List<Route> = emptyList(),
    var sortOrder: SortOrder? = null
)
