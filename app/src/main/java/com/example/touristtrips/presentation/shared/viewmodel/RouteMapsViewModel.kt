package com.example.touristtrips.presentation.shared.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.model.RouteDirection
import com.example.touristtrips.domain.shared.use_case.GetRouteDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteMapsViewModel @Inject constructor(
    private val getRouteDirections: GetRouteDirections
) : ViewModel() {

    private val _routeDirections = MutableLiveData<List<RouteDirection>>()
    val routeDirections: MutableLiveData<List<RouteDirection>> = _routeDirections

    fun getRouteDirections(locations: List<Location>) {
        viewModelScope.launch {
            _routeDirections.value = getRouteDirections.getRouteDirections(locations)
        }
    }

}