package com.example.touristtrips.data.remote.retrofit.repository

import com.example.touristtrips.data.remote.retrofit.DirectionsResponseResource
import com.example.touristtrips.data.remote.retrofit.source.DirectionsApi
import com.example.touristtrips.data.remote.retrofit.dto.DirectionResponses
import com.example.touristtrips.domain.shared.repository.DirectionsRepository
import com.example.touristtrips.domain.shared.util.Constants
import com.example.touristtrips.domain.shared.util.convertLatLngToString
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