package com.example.touristtrips.presentation.shared.viewmodel

import com.example.touristtrips.domain.shared.model.route.Route

data class RouteLocationsState(
    var route: Route = Route(),
    var locations: List<String> = emptyList()
)
