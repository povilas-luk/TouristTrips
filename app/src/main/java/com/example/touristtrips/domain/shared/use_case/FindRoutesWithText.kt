package com.example.touristtrips.domain.shared.use_case

import com.example.touristtrips.domain.shared.model.route.Route

class FindRoutesWithText {

    fun findRoutesWithText(text: String, routes: List<Route>): ArrayList<Route> {
        val foundRoutes = ArrayList<Route>()

        routes.forEach { route ->
            if (route.title.lowercase().contains(text.lowercase()) || text.isEmpty()) {
                foundRoutes.add(route)
            }
        }
        return foundRoutes
    }
}