package com.example.touristtrips.feature_route.presentation.all_routes_list

import com.example.touristtrips.feature_route.domain.model.Route

data class RoutesState(
    var routes: List<Route> = emptyList(),
)
