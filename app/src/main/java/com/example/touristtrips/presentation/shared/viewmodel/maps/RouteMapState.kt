package com.example.touristtrips.presentation.shared.viewmodel.maps

import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

data class RouteMapState(
    val routeMapData: RouteMapData,
    val localRoute: Boolean,
) {
    data class RouteMapData(
        val markerDataList: List<MapMarkerData>,
        val mapPolylineList: List<PolylineOptions>,
        val routeDistance: Double
    ) {
        data class MapMarkerData(
            val markerOptions: MarkerOptions,
            val locationId: String,
            val locationTitle: String,
            val locationImageUrl: String,
            val locationSequence: Int,
        )
    }
}
