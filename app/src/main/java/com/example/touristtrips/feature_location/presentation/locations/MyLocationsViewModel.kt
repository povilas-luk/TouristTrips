package com.example.touristtrips.feature_location.presentation.locations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val locationsState: MutableLiveData<LocationState> = _locationsState

    private var getLocationsJob: Job? = null

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

        getLocationsJob?.cancel()
        getLocationsJob = locationUseCases.getLocations.invoke()
            .onEach { locations ->
                _locationsState.value = locationsState.value?.copy(
                    locations = locations as ArrayList<Location>,
                )
            }
            .launchIn(viewModelScope)
    }

}