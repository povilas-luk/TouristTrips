package com.example.touristtrips.core.presentation.routes.route

import com.example.touristtrips.feature_route.domain.model.Route

data class RoutesState(
    var routes: List<Route> = emptyList(),
)
