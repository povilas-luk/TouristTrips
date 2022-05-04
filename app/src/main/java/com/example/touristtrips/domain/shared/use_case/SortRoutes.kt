package com.example.touristtrips.domain.shared.use_case

import com.example.touristtrips.domain.my_routes.model.Route
import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.domain.shared.util.SortType

class SortRoutes {

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

}