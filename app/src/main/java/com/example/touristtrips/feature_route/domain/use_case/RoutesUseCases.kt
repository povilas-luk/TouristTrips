package com.example.touristtrips.feature_route.domain.use_case

class RoutesUseCases(
    val addRoute: AddRoute,
    val getRoutes: GetRoutes,
    val getRoute: GetRoute,
    val updateRoute: UpdateRoute,
    val deleteRoute: DeleteRoute,
    val addRouteLocation: AddRouteLocation,
    val getRouteWithLocations: GetRouteWithLocations,
    val getRoutesWithLocations: GetRoutesWithLocations,
    val deleteRouteLocation: DeleteRouteLocation
)