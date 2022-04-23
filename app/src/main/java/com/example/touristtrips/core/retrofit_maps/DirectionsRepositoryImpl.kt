package com.example.touristtrips.core.retrofit_maps

import com.example.touristtrips.core.util.Constants
import com.example.touristtrips.core.domain.model.maps_directions_response_model.DirectionResponses
import com.example.touristtrips.core.domain.repository.DirectionsRepository
import com.example.touristtrips.core.util.convertLatLngToString
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject


class DirectionsRepositoryImpl @Inject constructor(
    private val directionsApi: DirectionsApi
): DirectionsRepository {

    override suspend fun getDirections(fromLatLng: LatLng, toLatLng: LatLng): DirectionsResponseResource<DirectionResponses> {

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

 /*   private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(@Query("origin") origin: String,
                         @Query("destination") destination: String,
                         @Query("key") apiKey: String): Call<DirectionResponses>
    }

    private object RetrofitClient {
        fun apiServices(context: Context): ApiServices {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.resources.getString(R.string.base_url))
                .build()

            return retrofit.create<ApiServices>(ApiServices::class.java)
        }
    }
}*/

}