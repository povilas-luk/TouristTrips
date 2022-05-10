package com.example.touristtrips.domain.shared.repository

import com.example.touristtrips.data.remote.retrofit.DirectionsResponseResource
import com.example.touristtrips.data.remote.retrofit.dto.DirectionResponses
import com.google.android.gms.maps.model.LatLng

interface DirectionsRepository {

    suspend fun getDirections(
        fromLatLng: LatLng,
        toLatLng: LatLng
    ): DirectionsResponseResource<DirectionResponses>
}