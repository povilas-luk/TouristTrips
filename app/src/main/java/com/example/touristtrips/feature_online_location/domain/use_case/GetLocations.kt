package com.example.touristtrips.feature_online_location.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.core.firebase_data.firebase_repository.LocationRepository
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.LocationState

class GetLocations {

    private val repository = LocationRepository()

    fun getLocations(liveData: MutableLiveData<LocationState>) {
        repository.getLocations(liveData)
    }
}