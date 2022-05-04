package com.example.touristtrips.domain.remote_routes.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.data.firebase.repository.RouteRepository
import com.example.touristtrips.presentation.shared.routes.route.RoutesState

class GetRoutes {

    private val routeRepository = RouteRepository()

    fun getRoutes(liveData: MutableLiveData<RoutesState>) {
        routeRepository.getRoutes(liveData)
    }
}