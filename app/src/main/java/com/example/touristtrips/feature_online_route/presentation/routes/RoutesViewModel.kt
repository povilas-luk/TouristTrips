package com.example.touristtrips.feature_online_route.presentation.routes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.touristtrips.core.firebase_data.firebase_repository.RouteRepository
import com.example.touristtrips.feature_location.presentation.locations.LocationState
import com.example.touristtrips.feature_online_route.domain.model.RouteLocation
import com.example.touristtrips.feature_online_route.domain.use_case.GetRouteLocations
import com.example.touristtrips.feature_online_route.domain.use_case.GetRouteWithLocationsId

import com.example.touristtrips.feature_route.presentation.all_routes_list.RoutesState

//@HiltViewModel
class RoutesViewModel : ViewModel() {

    private val repository = RouteRepository()

    private val _routesState = MutableLiveData<RoutesState>()
    val routesState: MutableLiveData<RoutesState> = _routesState

    private val _routeWithLocationsId = MutableLiveData<RouteLocationsState>()
    val routeWithLocationsId: MutableLiveData<RouteLocationsState> = _routeWithLocationsId

    private val _routeLocations = MutableLiveData<LocationState>()
    val routeLocations: MutableLiveData<LocationState> = _routeLocations

    fun getRoutes() {
        repository.getRoutes(_routesState)
    }

    fun getRouteWithLocationsId(routeId: String) {
        GetRouteWithLocationsId().getRouteWithLocations(_routeWithLocationsId, routeId)
    }

    fun getRouteLocations() {
        val locations = _routeWithLocationsId.value?.locations
        if (!locations.isNullOrEmpty()) {
            GetRouteLocations().getRouteLocations(_routeLocations, locations)
        }

    }



}