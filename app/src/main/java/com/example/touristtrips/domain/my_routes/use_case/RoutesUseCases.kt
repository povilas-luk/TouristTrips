package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.shared.use_case.FindRoutesWithText
import com.example.touristtrips.domain.shared.use_case.SortLocations
import com.example.touristtrips.domain.shared.use_case.SortRoutes

class RoutesUseCases(
    val addRoute: AddRoute,
    val getRoutes: GetRoutes,
    val getRoute: GetRoute,
    val updateRoute: UpdateRoute,
    val deleteRoute: DeleteRoute,
    val addRouteLocation: AddRouteLocation,
    val getRouteWithLocations: GetRouteWithLocations,
    val getRoutesWithLocations: GetRoutesWithLocations,
    val deleteRouteLocation: DeleteRouteLocation,
    val sortRoutes: SortRoutes,
    val findRoutesWithText: FindRoutesWithText
)