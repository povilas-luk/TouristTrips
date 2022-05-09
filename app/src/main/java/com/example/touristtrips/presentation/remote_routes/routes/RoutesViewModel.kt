package com.example.touristtrips.presentation.remote_routes.routes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.touristtrips.data.firebase.repository.RouteRepository
import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.domain.shared.util.route.findRoutesWithText
import com.example.touristtrips.presentation.shared.locations.location.LocationState
import com.example.touristtrips.presentation.shared.routes.route.RouteLocationsState
import com.example.touristtrips.domain.remote_routes.use_case.GetRouteLocations
import com.example.touristtrips.domain.remote_routes.use_case.GetRouteWithLocationsId

import com.example.touristtrips.presentation.shared.routes.route.RoutesState
import com.example.touristtrips.domain.my_routes.model.Route
import com.example.touristtrips.domain.remote_routes.use_case.GetRoutes
import com.example.touristtrips.domain.shared.use_case.FindRoutesWithText
import com.example.touristtrips.domain.shared.use_case.SortRoutes

//@HiltViewModel
class RoutesViewModel : ViewModel() {

    private val _routesState = MutableLiveData<RoutesState>()
    val routesState: LiveData<RoutesState> = _routesState

    private val _routeWithLocationsId = MutableLiveData<RouteLocationsState>()
    val routeWithLocationsId: LiveData<RouteLocationsState> = _routeWithLocationsId

    private val _routeLocations = MutableLiveData<LocationState>()
    val routeLocations: LiveData<LocationState> = _routeLocations

    private var allRoutesLiveData: MutableLiveData<List<Route>>? = null

    fun getRoutes() {
        //repository.getRoutes(_routesState)
        GetRoutes().getRoutes(_routesState)
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