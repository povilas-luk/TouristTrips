package com.example.touristtrips.domain.remote_routes.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.data.remote.firebase.repository.RouteRepository
import com.example.touristtrips.presentation.shared.viewmodel.RoutesState

class GetRoutes {

    private val routeRepository = RouteRepository()

    fun getRoutes(liveData: MutableLiveData<RoutesState>) {
        routeRepository.getRoutes(liveData)
    }
}