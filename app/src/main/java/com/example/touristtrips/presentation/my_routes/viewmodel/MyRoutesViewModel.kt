package com.example.touristtrips.presentation.my_routes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.presentation.shared.viewmodel.RoutesState
import com.example.touristtrips.domain.shared.model.route.Route
import com.example.touristtrips.domain.my_routes.use_case.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyRoutesViewModel @Inject constructor(
    private val routesUseCases: RoutesUseCases
) : ViewModel() {

    private val _routesState = MutableLiveData(RoutesState())
    val routesState: MutableLiveData<RoutesState> = _routesState

    private val allRoutesLiveData = MutableLiveData<List<Route>>()

    private var getRoutesJob: Job? = null

    init {
        getRoutes()
    }

    private fun getRoutes() {

        getRoutesJob?.cancel()
        getRoutesJob = routesUseCases.getRoutes()
            .onEach { routes ->
                _routesState.value = routesState.value?.copy(
                    routes = routes,
                )
                allRoutesLiveData.value = routes
            }
            .launchIn(viewModelScope)
    }

    fun showRoutesWithText(text: String) {
        val routes = routesUseCases.findRoutesWithText.findRoutesWithText(text, allRoutesLiveData.value ?: emptyList())

        _routesState.value = RoutesState(routes)
    }

    fun sortRoutes(sortOrder: SortOrder) {
        val routes = routesUseCases.sortRoutes.sortRoutes(
            sortOrder,
            allRoutesLiveData.value ?: emptyList()
        )
        allRoutesLiveData.value = ArrayList(routes)
        _routesState.value = RoutesState(ArrayList(routes), sortOrder = sortOrder)
    }

}