package com.example.touristtrips.domain.shared.model

import com.google.android.gms.maps.model.PolylineOptions

data class RouteDirections(
    val polylines: ArrayList<PolylineOptions> = ArrayList(),
    val distance: Int = 0,
)