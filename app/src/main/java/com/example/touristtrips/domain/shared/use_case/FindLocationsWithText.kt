package com.example.touristtrips.domain.shared.use_case

import com.example.touristtrips.domain.my_locations.model.Location

class FindLocationsWithText {

    fun findLocationsWithText(text: String, locations: List<Location>): ArrayList<Location> {
        val foundLocations = ArrayList<Location>()

        locations.forEach { location ->
            if (location.title.lowercase().contains(text.lowercase()) || text.isEmpty()) {
                foundLocations.add(location)
            }
        }
        return foundLocations
    }
}