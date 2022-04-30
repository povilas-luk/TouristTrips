package com.example.touristtrips.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class Polyline(
    @SerializedName("points")
    var points: String?
)