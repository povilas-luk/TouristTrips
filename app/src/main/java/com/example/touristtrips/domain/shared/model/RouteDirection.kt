package com.example.touristtrips.domain.shared.model

import com.google.android.gms.maps.model.PolylineOptions

data class RouteDirection(
    val polyline: PolylineOptions = PolylineOptions(),
    val distance: Double = 0.0,
)