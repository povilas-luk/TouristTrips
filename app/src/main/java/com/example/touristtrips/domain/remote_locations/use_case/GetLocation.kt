package com.example.touristtrips.domain.remote_locations.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.data.firebase.repository.LocationRepository
import com.example.touristtrips.presentation.shared.locations.location.LocationState

class GetLocation {

    private val repository = LocationRepository()

    fun getLocation(liveData: MutableLiveData<LocationState>, locationId: String) {
        repository.getLocation(liveData, locationId)
    }
}