package com.example.touristtrips.feature_online_location.domain.use_case

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.touristtrips.core.firebase_data.firebase_repository.LocationRepository
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.LocationState

class GetLocation {

    private val repository = LocationRepository()

    fun getLocation(liveData: MutableLiveData<LocationState>, locationId: String) {
        repository.getLocation(liveData, locationId)
    }
}