package com.example.touristtrips.feature_location.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.repository.LocalLocationRepository
import com.example.touristtrips.feature_location.presentation.locations.LocationState
import kotlinx.coroutines.flow.Flow

class GetLocations(
    private val repositoryLocal: LocalLocationRepository
) {
    operator fun invoke(): Flow<List<Location>> {
        return repositoryLocal.getLocations()
    }
}