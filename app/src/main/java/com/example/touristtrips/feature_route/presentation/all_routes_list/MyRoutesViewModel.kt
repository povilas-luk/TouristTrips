package com.example.touristtrips.feature_route.presentation.all_routes_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.feature_route.domain.use_case.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyRoutesViewModel @Inject constructor(
    private val routesUseCases: RoutesUseCases
): ViewModel() {

    private val _routesState = MutableLiveData(RoutesState())
    val routesState: MutableLiveData<RoutesState> = _routesState

    private var getRoutesJob: Job? = null

    init {
        getRoutes()
    }

    private fun getRoutes() {
        /*viewModelScope.launch {
            locationUseCases.getLocations().onEach { location ->
                _locationsState.value = locationsState.value?.copy(
                    locations = location
                )
            }
            //_state.value?.locations
        }*/

        getRoutesJob?.cancel()
        getRoutesJob = routesUseCases.getRoutes()
            .onEach { routes ->
                _routesState.value = routesState.value?.copy(
                    routes = routes,
                )
            }
            .launchIn(viewModelScope)
    }

}