package com.example.touristtrips.core.presentation.routes.route

import com.example.touristtrips.core.domain.util.SortOrder
import com.example.touristtrips.feature_route.domain.model.Route

data class RoutesState(
    var routes: List<Route> = emptyList(),
    var sortOrder: SortOrder? = null
)
