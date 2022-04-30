package com.example.touristtrips.presentation.shared.routes.route

import com.example.touristtrips.domain.my_routes.model.Route

data class RouteLocationsState(
    var route: Route = Route(),
    var locations: List<String> = emptyList()
)
