package com.example.touristtrips.data.remote.retrofit.dto

import com.google.gson.annotations.SerializedName

data class Northeast(
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("lng")
    var lng: Double?
)