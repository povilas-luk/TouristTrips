package com.example.touristtrips.domain.remote_routes.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.data.firebase.repository.LocationRepository
import com.example.touristtrips.presentation.shared.locations.location.LocationState

class GetRouteLocations {

    private val locationRepository = LocationRepository()

    fun getRouteLocations(liveData: MutableLiveData<LocationState>, locationsId: List<String>) {
        locationRepository.getLocationsWithIds(liveData, locationsId)
    }
}