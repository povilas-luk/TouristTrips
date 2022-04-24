package com.example.touristtrips.feature_location.domain.use_case

import com.example.touristtrips.core.domain.util.location.checkLocationFormatErrors
import com.example.touristtrips.feature_location.domain.model.InvalidLocationException
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocalLocationRepository

class AddLocation(
    private val repositoryLocal: LocalLocationRepository
) {

    @Throws(InvalidLocationException::class)
    suspend operator fun invoke(location: Location) {
        checkLocationFormatErrors(location)
        repositoryLocal.insertLocation(location)
    }

}