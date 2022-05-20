package com.example.touristtrips.presentation.my_locations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.domain.shared.model.SortOrder
import com.example.touristtrips.presentation.shared.viewmodel.locations.LocationState
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_locations.use_case.MyLocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyLocationsViewModel @Inject constructor(
    private val locationUseCases: MyLocationUseCases
): ViewModel() {

    private val _locationsState = MutableLiveData(LocationState())
    val locationsState: LiveData<LocationState> = _locationsState

    private val allLocationsLiveData = MutableLiveData<List<Location>>()

    private var getLocationsJob: Job? = null

    init {
        getLocations()
    }

    private fun getLocations() {
        getLocationsJob?.cancel()
        getLocationsJob = locationUseCases.getLocations.invoke()
            .onEach { locations ->
                _locationsState.value = locationsState.value?.copy(
                    locations = locations as ArrayList<Location>,
                )
                allLocationsLiveData.value = locations
            }
            .launchIn(viewModelScope)
    }

    fun showLocationsWithText(text: String) {
        val locations = locationUseCases.findLocationsWithText.findLocationsWithText(text, allLocationsLiveData.value ?: emptyList())

        _locationsState.value = LocationState(locations)
    }

    fun sortLocations(sortOrder: SortOrder) {
        val locations = locationUseCases.sortLocations.sortLocations(sortOrder, allLocationsLiveData.value ?: emptyList())
        allLocationsLiveData.value = ArrayList(locations)
        _locationsState.value = LocationState(ArrayList(locations), sortOrder = sortOrder)
    }

}
