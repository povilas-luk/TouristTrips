package com.example.touristtrips.presentation.shared.viewmodel

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.data.remote.retrofit.DirectionsResponseResource
import com.example.touristtrips.domain.shared.repository.DirectionsRepository
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.use_case.GetRouteDirectionsPolylines
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteMapsViewModel @Inject constructor(
    private val getRouteDirectionsPolylines: GetRouteDirectionsPolylines
) : ViewModel() {

    private val _directionsPolylines = MutableLiveData<List<PolylineOptions>>()
    val directionsPolylines: MutableLiveData<List<PolylineOptions>> = _directionsPolylines

    fun getDirectionsPolylines(locations: List<Location>) {

        viewModelScope.launch {
            _directionsPolylines.postValue(getRouteDirectionsPolylines.getDirectionsPolylines(locations))
        }

    }
}