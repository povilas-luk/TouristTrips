package com.example.touristtrips.core.domain.util.route

import com.example.touristtrips.core.domain.util.SortOrder
import com.example.touristtrips.core.domain.util.SortType
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_route.domain.model.InvalidRouteException
import com.example.touristtrips.feature_route.domain.model.Route

fun findRoutesWithText(text: String, routes: List<Route>): ArrayList<Route> {
    val foundRoutes = ArrayList<Route>()

    routes.forEach { route ->
        if (route.title.lowercase().contains(text.lowercase()) || text.isEmpty()) {
            foundRoutes.add(route)
        }
    }
    return foundRoutes
}

fun sortRoutes(sortOrder: SortOrder, routes: List<Route>): List<Route> {
    return when (sortOrder.sortType) {
        is SortType.Descending -> {
            when (sortOrder) {
                is SortOrder.City -> routes.sortedByDescending { it.city.lowercase() }
                is SortOrder.Type -> routes.sortedByDescending { it.type.lowercase() }
                is SortOrder.TimeToVisit -> routes.sortedByDescending { it.months_to_visit.lowercase() }
                is SortOrder.Title -> routes.sortedByDescending { it.title.lowercase() }
            }
        }
        is SortType.Ascending -> {
            when (sortOrder) {
                is SortOrder.City -> routes.sortedBy { it.city.lowercase() }
                is SortOrder.Type -> routes.sortedBy { it.type.lowercase() }
                is SortOrder.TimeToVisit -> routes.sortedBy { it.months_to_visit.lowercase() }
                is SortOrder.Title -> routes.sortedBy { it.title.lowercase() }
            }
        }
    }
}

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