package com.example.touristtrips.core.util.route

import com.example.touristtrips.feature_route.domain.model.InvalidRouteException
import com.example.touristtrips.feature_route.domain.model.Route

fun checkRouteFormatErrors(route: Route) {
    if (route.title.isBlank()) {
        throw InvalidRouteException("Title of the route can't be empty.")
    }
    if (route.type.isBlank()) {
        throw InvalidRouteException("Type of the route can't be empty.")
    }
    if (route.description.isBlank()) {
        throw InvalidRouteException("Description of the route can't be empty.")
    }
    if (route.city.isBlank()) {
        throw InvalidRouteException("City of the route can't be empty.")
    }
    if (route.months_to_visit.isBlank()) {
        throw InvalidRouteException("Time to visit of the route can't be empty.")
    }
    /*if (route.imageUrl.isBlank()) {
        throw InvalidRouteException("Image of the route can't be empty.")
    }*/
}