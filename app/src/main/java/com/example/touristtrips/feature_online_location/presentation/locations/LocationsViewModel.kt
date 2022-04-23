package com.example.touristtrips.feature_online_location.presentation.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.touristtrips.core.presentation.locations.location.LocationState
import com.example.touristtrips.core.util.location.findLocationsWithText
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_online_location.domain.use_case.GetLocation
import com.example.touristtrips.feature_online_location.domain.use_case.GetLocations

//@HiltViewModel
class LocationsViewModel: ViewModel() {

    private val _locationsState = MutableLiveData<LocationState>()
    val locationsState: LiveData<LocationState> = _locationsState

    private var allLocationsLiveData: MutableLiveData<List<Location>>? = null

    fun getLocations() {
        GetLocations().getLocations(_locationsState)
    }

    fun setAllLocations() {
        if (allLocationsLiveData == null) {
            allLocationsLiveData = MutableLiveData(_locationsState.value?.locations)
        }
    }

    fun getLocation(locationId: String) {
        GetLocation().getLocation(_locationsState, locationId)
    }

    fun showLocationsWithText(text: String) {
        val locations = findLocationsWithText(text, allLocationsLiveData?.value ?: emptyList())

        _locationsState.value = LocationState(locations)
    }

}