package com.example.touristtrips.presentation.shared.viewmodel

import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.domain.shared.model.route.Route

data class RoutesState(
    var routes: List<Route> = emptyList(),
    var sortOrder: SortOrder? = null
)
