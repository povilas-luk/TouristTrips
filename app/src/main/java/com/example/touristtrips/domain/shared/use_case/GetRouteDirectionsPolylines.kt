package com.example.touristtrips.domain.shared.use_case

import android.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.data.remote.retrofit.DirectionsResponseResource
import com.example.touristtrips.data.remote.retrofit.repository.DirectionsRepositoryImpl
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.repository.DirectionsRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetRouteDirectionsPolylines(
    private val repository: DirectionsRepository
) {

    suspend fun getDirectionsPolylines(locations: List<Location>): ArrayList<PolylineOptions> {
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
                        directionsResponse.data?.routes?.get(0)?.legs?.get(0)?.distance
                        val polyline = PolylineOptions()
                            .addAll(PolyUtil.decode(shape))
                            .width(8f)
                            .color(Color.RED)
                        polylines.add(polyline)
                    }
                }
            }
        }
        return polylines
    }
}