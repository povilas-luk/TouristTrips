package com.example.touristtrips.feature_online_route.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.core.firebase_data.firebase_repository.LocationRepository
import com.example.touristtrips.core.firebase_data.firebase_repository.RouteRepository
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_online_route.domain.model.RouteLocation
import com.example.touristtrips.feature_online_route.presentation.routes.RouteLocationsState
import com.example.touristtrips.feature_route.domain.model.Route

class GetRouteWithLocationsId {

    private val routeRepository = RouteRepository()

    fun getRouteWithLocations(liveData: MutableLiveData<RouteLocationsState>, routeId: String) {
        routeRepository.getRouteWithLocationsId(liveData, routeId)
    }


}