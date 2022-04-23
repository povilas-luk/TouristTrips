package com.example.touristtrips.feature_online_route.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.core.data.firebase_data.firebase_repository.LocationRepository
import com.example.touristtrips.core.presentation.locations.location.LocationState

class GetRouteLocations {

    private val locationRepository = LocationRepository()

    fun getRouteLocations(liveData: MutableLiveData<LocationState>, locationsId: List<String>) {
        locationRepository.getLocationsWithIds(liveData, locationsId)
    }
}