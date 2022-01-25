package com.example.touristtrips.feature_location.presentation.locations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.feature_location.domain.use_case.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases
): ViewModel() {

    private val _locationsState = MutableLiveData(LocationState())
    val locationsState: MutableLiveData<LocationState> = _locationsState

    private var getNotesJob: Job? = null

    init {
        getLocations()
    }

    private fun getLocations() {
        /*viewModelScope.launch {
            locationUseCases.getLocations().onEach { location ->
                _locationsState.value = locationsState.value?.copy(
                    locations = location
                )
            }
            //_state.value?.locations
        }*/

        getNotesJob?.cancel()
        getNotesJob = locationUseCases.getLocations()
            .onEach { locations ->
                _locationsState.value = locationsState.value?.copy(
                    locations = locations,
                )
            }
            .launchIn(viewModelScope)
    }

}