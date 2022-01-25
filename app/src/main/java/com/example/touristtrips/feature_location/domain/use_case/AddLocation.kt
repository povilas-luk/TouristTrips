package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.feature_location.domain.model.InvalidLocationException
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocationRepository

class AddLocation(
    private val repository: LocationRepository
) {

    @Throws(InvalidLocationException::class)
    suspend operator fun invoke(location: Location) {
        if (location.type.isBlank()) {
            throw InvalidLocationException("The type of the location can't be empty.")
        }
        if (location.title.isBlank()) {
            throw InvalidLocationException("The title of the location can't be empty.")
        }
        if (location.description.isBlank()) {
            throw InvalidLocationException("The content of the location can't be empty.")
        }
        if (location.latitude.isBlank()) {
            throw InvalidLocationException("The latitude of the location can't be empty.")
        }
        if (location.longitude.isBlank()) {
            throw InvalidLocationException("The longitude of the location can't be empty.")
        }
        if (location.city.isBlank()) {
            throw InvalidLocationException("The city of the location can't be empty.")
        }
        if (location.imageUrl.isBlank()) {
            throw InvalidLocationException("The image of the location can't be empty.")
        }
        if (location.months_to_visit.isBlank()) {
            throw InvalidLocationException("The months_to_visit of the location can't be empty.")
        }

        repository.insertLocation(location)
    }

}