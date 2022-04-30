package com.example.touristtrips.domain.shared.repository

import com.example.touristtrips.data.retrofit.DirectionsResponseResource
import com.example.touristtrips.data.retrofit.dto.DirectionResponses
import com.google.android.gms.maps.model.LatLng

interface DirectionsRepository {

    suspend fun getDirections(
        fromLatLng: LatLng,
        toLatLng: LatLng
    ): DirectionsResponseResource<DirectionResponses>
}