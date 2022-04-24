package com.example.touristtrips.core.domain.util

import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_route.domain.model.Route
import com.google.android.gms.maps.model.LatLng

enum class Operation(val displayName: String) {
    SAVED("saved"),
    UPDATED("updated"),
    DELETED("deleted"),
    FOUND("found"),
    ADDED("added"),
    RL_DELETED("location deleted"),
    RL_ADDED("location added")
}

fun convertLatLngToString(latLng: LatLng): String {
    return latLng.latitude.toString() + "," + latLng.longitude.toString()
}

fun findRoutesWithText(text: String, routes: List<Route>): ArrayList<Route> {
    val foundRoutes = ArrayList<Route>()

    routes.forEach { route ->
        if (route.title.lowercase().contains(text.lowercase()) || text.isEmpty()) {
            foundRoutes.add(route)
        }
    }
    return foundRoutes
}

