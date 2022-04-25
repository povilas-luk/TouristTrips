package com.example.touristtrips.core.domain.model.maps_directions_response_model

import com.google.gson.annotations.SerializedName

data class Distance(
    @SerializedName("text")
    var text: String?,
    @SerializedName("value")
    var value: Int?
)