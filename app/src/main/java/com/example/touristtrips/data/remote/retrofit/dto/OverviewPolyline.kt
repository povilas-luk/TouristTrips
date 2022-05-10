package com.example.touristtrips.data.remote.retrofit.dto

import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
    @SerializedName("points")
    var points: String?
)