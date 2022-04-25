package com.example.touristtrips.core.data.retrofit_data.retrofit_data_source

import com.example.touristtrips.core.domain.model.maps_directions_response_model.DirectionResponses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApi {
    @GET("/maps/api/directions/json")
    suspend fun getDirection(@Query("origin") origin: String,
                     @Query("destination") destination: String,
                     @Query("key") apiKey: String): Response<DirectionResponses>
}