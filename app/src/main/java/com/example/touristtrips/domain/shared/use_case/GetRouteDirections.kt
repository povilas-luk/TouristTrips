package com.example.touristtrips.domain.shared.use_case

import android.graphics.Color
import com.example.touristtrips.data.remote.retrofit.DirectionsResponseResource
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.model.RouteDirection
import com.example.touristtrips.domain.shared.repository.DirectionsRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil


class GetRouteDirections(
    private val repository: DirectionsRepository
) {

    suspend fun getRouteDirections(locations: List<Location>): ArrayList<RouteDirection> {
        val routeDirections = ArrayList<RouteDirection>()
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
                            directionsResponse.data?.routes?.get(0)?.overviewPolyline?.points ?: ""
                        val distance = (directionsResponse.data?.routes?.get(0)?.legs?.get(0)?.distance?.value ?: 0)/1000.0
                        val polyline = PolylineOptions()
                            .addAll(PolyUtil.decode(shape))
                            .width(8f)
                            .color(Color.RED)
                        routeDirections.add(RouteDirection(polyline, distance))
                    }
                }
            }
        }
        return routeDirections
    }
}