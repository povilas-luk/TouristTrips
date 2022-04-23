package com.example.touristtrips.feature_online_route.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.core.data.firebase_data.firebase_repository.RouteRepository
import com.example.touristtrips.core.presentation.routes.route.RouteLocationsState

class GetRouteWithLocationsId {

    private val routeRepository = RouteRepository()

    fun getRouteWithLocations(liveData: MutableLiveData<RouteLocationsState>, routeId: String) {
        routeRepository.getRouteWithLocationsId(liveData, routeId)
    }


}