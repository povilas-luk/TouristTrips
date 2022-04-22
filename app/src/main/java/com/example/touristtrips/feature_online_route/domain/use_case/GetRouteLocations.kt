package com.example.touristtrips.feature_online_route.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.core.firebase_data.firebase_repository.LocationRepository
import com.example.touristtrips.core.firebase_data.firebase_repository.RouteRepository
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.LocationState
import com.example.touristtrips.feature_online_route.presentation.routes.RouteLocationsState

class GetRouteLocations {

    private val locationRepository = LocationRepository()

    fun getRouteLocations(liveData: MutableLiveData<LocationState>, locationsId: List<String>) {
        locationRepository.getLocationsWithIds(liveData, locationsId)
    }
}