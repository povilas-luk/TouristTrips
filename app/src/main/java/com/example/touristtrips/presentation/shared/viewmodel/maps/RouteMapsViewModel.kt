package com.example.touristtrips.presentation.shared.viewmodel.maps

import android.location.Geocoder
import androidx.lifecycle.*
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.model.route.RouteDirection
import com.example.touristtrips.domain.shared.model.route.RouteWithLocations
import com.example.touristtrips.domain.shared.use_case.RouteMapUseCases
import com.example.touristtrips.presentation.shared.viewmodel.locations.LocationState
import com.example.touristtrips.presentation.shared.viewmodel.route.RouteLocationsState
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteMapsViewModel @Inject constructor(
    private val routeMapUseCases: RouteMapUseCases,
    private val state: SavedStateHandle
) : ViewModel() {

    private lateinit var routeId: String
    private var localRoute: Boolean = false

    private val _routeMapState = MediatorLiveData<RouteMapState>()
    val routeMapState: MediatorLiveData<RouteMapState> = _routeMapState

    private val _remoteRouteWithLocationsId = MutableLiveData<RouteLocationsState>()
    private val _remoteRouteLocations = MutableLiveData<LocationState>()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        data class ShowLocationSequenceDialog(
            val sequence: Int,
            val locationId: String,
            val locationTitle: String,
            val locationImageUrl: String,
            val locationsSequenceArray: Array<Int>,
        ): UiEvent()
    }

    sealed class MapEvent {
        data class MarkerSelected(val marker: Marker): MapEvent()
        data class LocationSequenceChanged(val locationId: String, val newSequence: Int): MapEvent()
    }

    init {
        val localRouteId = state.get<String>("myRouteId")
        val remoteRouteId = state.get<String>("routeId")
        if (localRouteId != null) {
            routeId = localRouteId
            localRoute = true
            setLocalRouteState()
        } else if (remoteRouteId != null){
            routeId = remoteRouteId
            setRemoteRouteData()
        }
    }

    fun onMapEvent(event: MapEvent) {
        when (event) {
            is MapEvent.MarkerSelected -> {
                viewModelScope.launch {
                    val locationSequenceData = getLocationSequenceDialogData(event.marker.tag.toString())
                    if (locationSequenceData != null && localRoute) {
                        _eventFlow.emit(locationSequenceData)
                    }
                }
            }
            is MapEvent.LocationSequenceChanged -> {
                viewModelScope.launch {
                    routeMapUseCases.updateRouteLocation.invoke(routeId, event.locationId, event.newSequence)
                    setLocalRouteState()
                }
            }
        }
    }

    private fun getLocationSequenceDialogData(locationId: String): UiEvent.ShowLocationSequenceDialog? {
        val markerMapData = _routeMapState.value?.routeMapData?.markerDataList?.find {it.locationId == locationId}
        return if (markerMapData != null) {
            UiEvent.ShowLocationSequenceDialog(
                sequence = markerMapData.locationSequence,
                locationId = markerMapData.locationId,
                locationTitle = markerMapData.locationTitle,
                locationImageUrl = markerMapData.locationImageUrl,
                locationsSequenceArray = getMarkerSizeArray(),
            )
        } else {null}
    }

    private fun getMarkerSizeArray(): Array<Int> {
        return Array(_routeMapState.value?.routeMapData?.markerDataList?.size ?: 0) {it + 1}
    }

    private fun setRemoteRouteData() {
        viewModelScope.launch {
            routeMapUseCases.getRouteWithLocationsId.getRouteWithLocations(_remoteRouteWithLocationsId, routeId)

            _routeMapState.addSource(_remoteRouteWithLocationsId
            ) { t ->
                if (!t?.locations.isNullOrEmpty())
                    routeMapUseCases.getRouteLocations.getRouteLocations(
                        _remoteRouteLocations,
                        t!!.locations
                    )
            }

            _routeMapState.addSource(_remoteRouteLocations
            ) { t ->
                if (!t?.locations.isNullOrEmpty()) {
                    viewModelScope.launch {
                        setRouteState(t!!.locations)
                    }
                }
            }
        }

    }

    private fun setLocalRouteState() {
        viewModelScope.launch {
            val routeWithLocations: RouteWithLocations? = routeMapUseCases.getRouteWithLocations.invoke(routeId)
            val locations = routeWithLocations?.locations ?: emptyList()
            setRouteState(locations)
        }
    }

    private suspend fun setRouteState(locations: List<Location>) {
        val mapMarkerData = getLocationsMarkers(locations)
        val routeMapData = getRouteMapData(mapMarkerData, getRouteDirections(locations))
        _routeMapState.value = RouteMapState(routeMapData, localRoute)
    }

    private fun getRouteMapData(markerDataList: List<RouteMapState.RouteMapData.MapMarkerData>, routeDirections: List<RouteDirection>): RouteMapState.RouteMapData {
        val routeDistance = routeDirections.sumOf { it.distance }
        val routePolylines = routeDirections.map { it.polyline }
        return RouteMapState.RouteMapData(markerDataList, routePolylines, routeDistance)
    }


    private suspend fun getRouteDirections(locations: List<Location>): List<RouteDirection> {
         return routeMapUseCases.getRouteDirections.getRouteDirections(locations)
    }

    private suspend fun getLocationsMarkers(locations: List<Location>): List<RouteMapState.RouteMapData.MapMarkerData> {
        val mapMarkerData = ArrayList<RouteMapState.RouteMapData.MapMarkerData>()
        if (!locations.isNullOrEmpty()) {
            locations.forEachIndexed { index, location ->
                val latLng: LatLng? = if (location.latitude.isNotEmpty() && location.longitude.isNotEmpty()) {
                    LatLng(location.latitude.toDouble(), location.longitude.toDouble())
                } else {
                    searchGeoCoder(location.location_search)
                }
                if (latLng != null) {
                    val markerOptions = MarkerOptions().position(latLng).title(location.title)
                    mapMarkerData.add(
                        RouteMapState.RouteMapData.MapMarkerData(
                            markerOptions = markerOptions,
                            locationId = location.locationId,
                            locationTitle = location.title,
                            locationImageUrl = location.imageUrl,
                            locationSequence = index
                        )
                    )
                } else {
                    _eventFlow.emit(UiEvent.ShowToast("Location \"${location.title}\" data not found!",))
                }
            }
        }
        return mapMarkerData
    }

    private fun searchGeoCoder(search: String): LatLng? {
        val listGeoCoder = Geocoder(routeMapUseCases.context).getFromLocationName(search, 1)
        return if (listGeoCoder.isNotEmpty()) {
            LatLng(listGeoCoder[0].latitude, listGeoCoder[0].longitude)
        } else {
            null
        }
    }

}