package com.example.touristtrips.domain.remote_routes.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.data.remote.firebase.repository.RouteRepository
import com.example.touristtrips.presentation.shared.viewmodel.RouteLocationsState

class GetRouteWithLocationsId {

    private val routeRepository = RouteRepository()

    fun getRouteWithLocations(liveData: MutableLiveData<RouteLocationsState>, routeId: String) {
        routeRepository.getRouteWithLocationsId(liveData, routeId)
    }


}