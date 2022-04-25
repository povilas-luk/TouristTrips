package com.example.touristtrips.core.domain.repository

import com.example.touristtrips.core.domain.model.maps_directions_response_model.DirectionResponses
import com.example.touristtrips.core.data.retrofit_data.DirectionsResponseResource
import com.google.android.gms.maps.model.LatLng

interface DirectionsRepository {

    suspend fun getDirections(fromLatLng: LatLng, toLatLng: LatLng): DirectionsResponseResource<DirectionResponses>
}