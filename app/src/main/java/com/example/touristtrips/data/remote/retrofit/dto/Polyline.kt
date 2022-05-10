package com.example.touristtrips.data.remote.retrofit.dto

import com.google.gson.annotations.SerializedName

data class Polyline(
    @SerializedName("points")
    var points: String?
)