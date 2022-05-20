package com.example.touristtrips.presentation.remote_routes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.touristtrips.domain.shared.model.SortOrder
import com.example.touristtrips.presentation.shared.viewmodel.locations.LocationState
import com.example.touristtrips.presentation.shared.viewmodel.route.RouteLocationsState

import com.example.touristtrips.presentation.shared.viewmodel.route.RoutesState
import com.example.touristtrips.domain.shared.model.route.Route
import com.example.touristtrips.domain.remote_routes.use_case.RouteUseCases
import com.example.touristtrips.domain.shared.use_case.FindRoutesWithText
import com.example.touristtrips.domain.shared.use_case.SortRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    private val routeUseCases: RouteUseCases
) : ViewModel() {

    private val _routesState = MutableLiveData<RoutesState>()
    val routesState: LiveData<RoutesState> = _routesState

    private val _routeWithLocationsId = MutableLiveData<RouteLocationsState>()
    val routeWithLocationsId: LiveData<RouteLocationsState> = _routeWithLocationsId

    private val _routeLocations = MutableLiveData<LocationState>()
    val routeLocations: LiveData<LocationState> = _routeLocations

    private var allRoutesLiveData: MutableLiveData<List<Route>>? = null

    fun getRoutes() {
        //repository.getRoutes(_routesState)
        routeUseCases.getRoutes.getRoutes(_routesState)
    }

    fun getRouteWithLocationsId(routeId: String) {
        routeUseCases.getRouteWithLocationsId.getRouteWithLocations(_routeWithLocationsId, routeId)
    }

    fun getRouteLocations() {
        val locations = _routeWithLocationsId.value?.locations
        if (!locations.isNullOrEmpty()) {
            routeUseCases.getRouteLocations.getRouteLocations(_routeLocations, locations)
        }
    }

    fun setAllRoutes() {
        if (allRoutesLiveData == null) {
            allRoutesLiveData = MutableLiveData(_routesState.value?.routes ?: emptyList())
        }
    }

    fun showRoutesWithText(text: String) {
        val routes = FindRoutesWithText().findRoutesWithText(text, allRoutesLiveData?.value ?: emptyList())

        _routesState.value = RoutesState(routes)
    }

    fun sortRoutes(sortOrder: SortOrder) {
        val routes = SortRoutes().sortRoutes(
            sortOrder,
            allRoutesLiveData?.value ?: emptyList()
        )
        allRoutesLiveData?.value = ArrayList(routes)
        _routesState.value = RoutesState(ArrayList(routes), sortOrder = sortOrder)
    }

}