package com.example.touristtrips.data.remote.retrofit.source

import com.example.touristtrips.data.remote.retrofit.dto.DirectionResponses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApi {
    @GET("/maps/api/directions/json")
    suspend fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String
    ): Response<DirectionResponses>
}