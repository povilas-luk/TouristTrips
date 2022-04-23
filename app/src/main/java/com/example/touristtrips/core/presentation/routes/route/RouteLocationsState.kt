package com.example.touristtrips.core.presentation.routes.route

import com.example.touristtrips.feature_route.domain.model.Route

data class RouteLocationsState(
    var route: Route = Route(),
    var locations: List<String> = emptyList()
)
