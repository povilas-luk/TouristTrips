package com.example.touristtrips.core.domain.util.location

import com.example.touristtrips.core.domain.util.SortOrder
import com.example.touristtrips.core.domain.util.SortType
import com.example.touristtrips.feature_location.domain.model.InvalidLocationException
import com.example.touristtrips.feature_location.domain.model.Location

fun findLocationsWithText(text: String, locations: List<Location>): ArrayList<Location> {
    val foundLocations = ArrayList<Location>()

    locations.forEach { location ->
        if (location.title.lowercase().contains(text.lowercase()) || text.isEmpty()) {
            foundLocations.add(location)
        }
    }
    return foundLocations
}

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

fun checkLocationFormatErrors(location: Location) {
    if (location.title.isBlank()) {
        throw InvalidLocationException("Title of the location can't be empty.")
    }
    if (location.type.isBlank()) {
        throw InvalidLocationException("Type of the location can't be empty.")
    }
    if (location.description.isBlank()) {
        throw InvalidLocationException("Content of the location can't be empty.")
    }
    if (location.location_search.isBlank()) {
        if (location.latitude.isBlank()) {
            throw InvalidLocationException("Location search or latitude and longitude can't be empty.")
        }
        if (location.latitude.toDoubleOrNull() == null) {
            throw InvalidLocationException("Latitude of the location must be a number.")
        }
        if (location.latitude.toDouble() > 90 || location.latitude.toDouble() < -90) {
            throw InvalidLocationException("Latitude of the location must be between -90 and 90.")
        }
        if (location.longitude.isBlank()) {
            throw InvalidLocationException("Location search or latitude and longitude can't be empty.")
        }
        if (location.longitude.toDoubleOrNull() == null) {
            throw InvalidLocationException("Longitude of the location must be a number.")
        }
        if (location.longitude.toDouble() > 180 || location.longitude.toDouble() < -180) {
            throw InvalidLocationException("Longitude of the location must be between -180 and 180.")
        }
    }
    if (location.city.isBlank()) {
        throw InvalidLocationException("City of the location can't be empty.")
    }
    if (location.months_to_visit.isBlank()) {
        throw InvalidLocationException("Time to visit of the location can't be empty.")
    }
    /*if (location.price.isBlank()) {
        throw InvalidLocationException("Price of the location can't be empty.")
    }
    if (location.price.toDoubleOrNull() == null) {
        throw InvalidLocationException("Price of the location must be a number.")
    }*/
    /*if (location.imageUrl.isBlank()) {
        throw InvalidLocationException("Image url of the location can't be empty.")
    }
    if (!location.imageUrl.startsWith("https://")) {
        throw InvalidLocationException("Image url of the location must start with https://.")
    }*/

}