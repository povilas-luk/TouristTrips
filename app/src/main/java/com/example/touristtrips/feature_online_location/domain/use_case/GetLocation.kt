package com.example.touristtrips.feature_online_location.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.core.data.firebase_data.firebase_repository.LocationRepository
import com.example.touristtrips.core.presentation.locations.location.LocationState

class GetLocation {

    private val repository = LocationRepository()

    fun getLocation(liveData: MutableLiveData<LocationState>, locationId: String) {
        repository.getLocation(liveData, locationId)
    }
}