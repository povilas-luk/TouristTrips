package com.example.touristtrips.core.presentation.routes.route_map

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.core.data.retrofit_data.DirectionsResponseResource
import com.example.touristtrips.core.domain.repository.DirectionsRepository
import com.example.touristtrips.feature_location.domain.model.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteMapsViewModel @Inject constructor(
    private val repository: DirectionsRepository
) : ViewModel() {

    private val _directionsPolylines = MutableLiveData<List<PolylineOptions>>()
    val directionsPolylines: MutableLiveData<List<PolylineOptions>> = _directionsPolylines

    fun getDirectionsPolylines(locations: List<Location>) {

        viewModelScope.launch {
            val polylines = ArrayList<PolylineOptions>()
            for (i in locations.indices) {
                if (i < locations.size - 1) {
                    val fromLatLng =
                        LatLng(locations[i].latitude.toDouble(), locations[i].longitude.toDouble())
                    val toLatLng = LatLng(
                        locations[i + 1].latitude.toDouble(),
                        locations[i + 1].longitude.toDouble()
                    )
                    when (val directionsResponse = repository.getDirections(fromLatLng, toLatLng)) {
                        is DirectionsResponseResource.Success -> {
                            val shape =
                                directionsResponse.data?.routes?.get(0)?.overviewPolyline?.points
                            val polyline = PolylineOptions()
                                .addAll(PolyUtil.decode(shape))
                                .width(8f)
                                .color(Color.RED)
                            polylines.add(polyline)
                        }
                    }
                }
            }
            _directionsPolylines.postValue(polylines)
        }

    }
}