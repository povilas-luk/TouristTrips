package com.example.touristtrips.domain.my_locations.use_case

import com.example.touristtrips.domain.shared.util.location.checkLocationFormatErrors
import com.example.touristtrips.domain.my_locations.model.InvalidLocationException
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_locations.repository.LocalLocationRepository

class AddLocation(
    private val repositoryLocal: LocalLocationRepository
) {

    @Throws(InvalidLocationException::class)
    suspend operator fun invoke(location: Location) {
        checkLocationFormatErrors(location)
        repositoryLocal.insertLocation(location)
    }

}