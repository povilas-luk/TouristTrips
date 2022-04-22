package com.example.touristtrips.feature_online_route.presentation.routes

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_route.domain.model.Route

data class RouteLocationsState(
    var route: Route = Route(),
    var locations: List<String> = emptyList()
)
