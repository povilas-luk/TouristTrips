package com.example.touristtrips.domain.shared.use_case

import android.content.Context
import com.example.touristtrips.domain.my_routes.use_case.GetRouteWithLocations
import com.example.touristtrips.domain.my_routes.use_case.UpdateRouteLocation
import com.example.touristtrips.domain.remote_routes.use_case.GetRouteLocations
import com.example.touristtrips.domain.remote_routes.use_case.GetRouteWithLocationsId

class RouteMapUseCases (
    val getRouteDirections: GetRouteDirections,
    val getRouteWithLocations: GetRouteWithLocations,
    val getRouteWithLocationsId: GetRouteWithLocationsId,
    val getRouteLocations: GetRouteLocations,
    val updateRouteLocation: UpdateRouteLocation,
    val context: Context
)