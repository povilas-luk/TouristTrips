package com.example.touristtrips.feature_online_route.presentation.routes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.touristtrips.core.data.firebase_data.firebase_repository.RouteRepository
import com.example.touristtrips.core.presentation.locations.location.LocationState
import com.example.touristtrips.core.presentation.routes.route.RouteLocationsState
import com.example.touristtrips.feature_online_route.domain.use_case.GetRouteLocations
import com.example.touristtrips.feature_online_route.domain.use_case.GetRouteWithLocationsId

import com.example.touristtrips.core.presentation.routes.route.RoutesState
import com.example.touristtrips.core.domain.util.findRoutesWithText
import com.example.touristtrips.feature_route.domain.model.Route

//@HiltViewModel
class RoutesViewModel : ViewModel() {

    private val repository = RouteRepository()

    private val _routesState = MutableLiveData<RoutesState>()
    val routesState: LiveData<RoutesState> = _routesState

    private val _routeWithLocationsId = MutableLiveData<RouteLocationsState>()
    val routeWithLocationsId: LiveData<RouteLocationsState> = _routeWithLocationsId

    private val _routeLocations = MutableLiveData<LocationState>()
    val routeLocations: LiveData<LocationState> = _routeLocations

    private var allRoutesLiveData: MutableLiveData<List<Route>>? = null

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

    fun setAllRoutes() {
        if (allRoutesLiveData == null) {
            allRoutesLiveData = MutableLiveData(_routesState.value?.routes ?: emptyList())
        }
    }

    fun showRoutesWithText(text: String) {
        val routes = findRoutesWithText(text, allRoutesLiveData?.value ?: emptyList())

        _routesState.value = RoutesState(routes)
    }

}