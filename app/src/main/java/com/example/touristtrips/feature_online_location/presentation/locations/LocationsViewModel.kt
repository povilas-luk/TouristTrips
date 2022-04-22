package com.example.touristtrips.feature_online_location.presentation.locations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.touristtrips.feature_location.presentation.locations.LocationState
import com.example.touristtrips.feature_online_location.domain.use_case.GetLocation
import com.example.touristtrips.feature_online_location.domain.use_case.GetLocations

//@HiltViewModel
class LocationsViewModel: ViewModel() {

    private val _locationsState = MutableLiveData<LocationState>()
    val locationsState: MutableLiveData<LocationState> = _locationsState

    fun getLocations() {
        GetLocations().getLocations(_locationsState)
    }

    fun getLocation(locationId: String) {
        GetLocation().getLocation(_locationsState, locationId)
    }



}