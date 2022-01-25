package com.example.touristtrips.feature_location.presentation.locations

import com.example.touristtrips.feature_location.domain.model.Location

sealed class LocationEvent {
    class Success(val resultText: String, val location: Location): LocationEvent()
    class Failure(val errorText: String): LocationEvent()
    object Loading : LocationEvent()
    object Empty: LocationEvent()
}