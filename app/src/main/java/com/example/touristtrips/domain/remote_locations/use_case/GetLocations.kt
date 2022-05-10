package com.example.touristtrips.domain.remote_locations.use_case

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.data.remote.firebase.repository.LocationRepository
import com.example.touristtrips.presentation.shared.viewmodel.LocationState

class GetLocations {

    private val repository = LocationRepository()

    fun getLocations(liveData: MutableLiveData<LocationState>) {
        repository.getLocations(liveData)
    }
}