package com.example.touristtrips.feature_location.presentation.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.core.findLocationsWithText
import com.example.touristtrips.core.presentation.locations.location.LocationState
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.use_case.MyLocationUseCases
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
        val locations = findLocationsWithText(text, allLocationsLiveData.value ?: emptyList())

        _locationsState.value = LocationState(locations)
    }

}