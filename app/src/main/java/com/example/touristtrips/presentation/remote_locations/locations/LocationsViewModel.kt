package com.example.touristtrips.presentation.remote_locations.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.presentation.shared.locations.location.LocationState
import com.example.touristtrips.domain.shared.util.location.findLocationsWithText
import com.example.touristtrips.domain.shared.util.location.sortLocations
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.remote_locations.use_case.GetLocation
import com.example.touristtrips.domain.remote_locations.use_case.GetLocations
import com.example.touristtrips.domain.shared.use_case.FindLocationsWithText
import com.example.touristtrips.domain.shared.use_case.SortLocations
import kotlin.collections.ArrayList

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
        val locations = FindLocationsWithText().findLocationsWithText(text, allLocationsLiveData?.value ?: emptyList())

        _locationsState.value = LocationState(locations)
    }

    fun sortLocations(sortOrder: SortOrder) {
        val locations = SortLocations().sortLocations(sortOrder, allLocationsLiveData?.value ?: emptyList())
        allLocationsLiveData?.value = ArrayList(locations)
        _locationsState.value = LocationState(ArrayList(locations), sortOrder = sortOrder)
    }

}