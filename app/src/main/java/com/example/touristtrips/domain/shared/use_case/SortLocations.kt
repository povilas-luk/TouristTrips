package com.example.touristtrips.domain.shared.use_case

import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.model.SortOrder
import com.example.touristtrips.domain.shared.model.SortType

class SortLocations {

    fun sortLocations(sortOrder: SortOrder, locations: List<Location>): List<Location> {
        return when (sortOrder.sortType) {
            is SortType.Descending -> {
                when (sortOrder) {
                    is SortOrder.City -> locations.sortedByDescending { it.city.lowercase() }
                    is SortOrder.Type -> locations.sortedByDescending { it.type.lowercase() }
                    is SortOrder.TimeToVisit -> locations.sortedByDescending { it.months_to_visit.lowercase() }
                    is SortOrder.Title -> locations.sortedByDescending { it.title.lowercase() }
                }
            }
            is SortType.Ascending -> {
                when (sortOrder) {
                    is SortOrder.City -> locations.sortedBy { it.city.lowercase() }
                    is SortOrder.Type -> locations.sortedBy { it.type.lowercase() }
                    is SortOrder.TimeToVisit -> locations.sortedBy { it.months_to_visit.lowercase() }
                    is SortOrder.Title -> locations.sortedBy { it.title.lowercase() }
                }
            }
        }
    }
}