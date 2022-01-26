package com.example.touristtrips.feature_location.presentation.locations

import com.example.touristtrips.feature_location.domain.model.Location

sealed class LocationEvent {
    class Success(val operation: Operation, val location: Location): LocationEvent()
    class Failure(val errorText: String): LocationEvent()
    object Loading : LocationEvent()
    object Empty: LocationEvent()
}

enum class Operation(val displayName: String) {
    SAVED("saved"),
    UPDATED("Updated"),
    DELETED("Deleted"),
    FOUND("Found")
}