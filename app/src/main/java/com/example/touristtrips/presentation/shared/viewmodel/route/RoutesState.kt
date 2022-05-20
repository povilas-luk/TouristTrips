package com.example.touristtrips.presentation.shared.viewmodel.route

import com.example.touristtrips.domain.shared.model.SortOrder
import com.example.touristtrips.domain.shared.model.route.Route

data class RoutesState(
    var routes: List<Route> = emptyList(),
    var sortOrder: SortOrder? = null
)
