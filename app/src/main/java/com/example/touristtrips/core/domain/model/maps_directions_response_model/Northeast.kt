package com.example.touristtrips.core.domain.model.maps_directions_response_model

import com.google.gson.annotations.SerializedName

data class Northeast(
        @SerializedName("lat")
        var lat: Double?,
        @SerializedName("lng")
        var lng: Double?
)