package com.example.touristtrips.core.data.retrofit_data.retrofit_repository

import com.example.touristtrips.core.data.retrofit_data.DirectionsResponseResource
import com.example.touristtrips.core.data.retrofit_data.retrofit_data_source.DirectionsApi
import com.example.touristtrips.core.domain.model.maps_directions_response_model.DirectionResponses
import com.example.touristtrips.core.domain.repository.DirectionsRepository
import com.example.touristtrips.core.domain.util.Constants
import com.example.touristtrips.core.domain.util.convertLatLngToString
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject


class DirectionsRepositoryImpl @Inject constructor(
    private val directionsApi: DirectionsApi
) : DirectionsRepository {

    override suspend fun getDirections(
        fromLatLng: LatLng,
        toLatLng: LatLng
    ): DirectionsResponseResource<DirectionResponses> {
        val fromString = convertLatLngToString(fromLatLng)
        val toString = convertLatLngToString(toLatLng)

        return try {
            val response = directionsApi.getDirection(fromString, toString, Constants.API_KEY)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                DirectionsResponseResource.Success(result)
            } else {
                DirectionsResponseResource.Error(response.message())
            }
        } catch (e: Exception) {
            DirectionsResponseResource.Error(e.message ?: "Error")
        }
    }

}