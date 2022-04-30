package com.example.touristtrips.presentation.my_routes.route

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.touristtrips.domain.shared.util.Operation
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_routes.model.InvalidRouteException
import com.example.touristtrips.domain.my_routes.model.Route
import com.example.touristtrips.domain.my_routes.use_case.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRouteViewModel @Inject constructor(
    private val routesUseCases: RoutesUseCases
) : ViewModel() {

    sealed class AddEditRouteEvent {
        data class SaveRoute(val route: Route) : AddEditRouteEvent()
        data class AddLocation(val routeId: String, val locationId: String) : AddEditRouteEvent()
        data class DeleteLocation(val routeId: String, val locationId: String) : AddEditRouteEvent()
        data class EditRoute(val route: Route) : AddEditRouteEvent()
        data class DeleteRoute(val route: Route) : AddEditRouteEvent()
    }

    sealed class RouteEvent {
        class Success(val operation: Operation, val route: Route) : RouteEvent()
        class Failure(val errorText: String) : RouteEvent()
        object Loading : RouteEvent()
        object Empty : RouteEvent()
    }

    private val _locationsListLiveData = MutableLiveData<List<Location>>()
    val locationsListLiveData: LiveData<List<Location>> = _locationsListLiveData

    private val _eventFlow = MutableSharedFlow<RouteEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getRoutesWithLocationsJob: Job? = null

    fun onEvent(event: AddEditRouteEvent) {
        when (event) {
            is AddEditRouteEvent.SaveRoute -> {
                viewModelScope.launch {
                    try {
                        routesUseCases.addRoute(
                            event.route
                        )
                        _eventFlow.emit(RouteEvent.Success(Operation.SAVED, event.route))
                    } catch (e: InvalidRouteException) {
                        _eventFlow.emit(
                            RouteEvent.Failure(
                                e.message ?: "Failed to save route"
                            )
                        )
                    }
                }
            }
            is AddEditRouteEvent.EditRoute -> {
                viewModelScope.launch {
                    try {
                        routesUseCases.updateRoute(
                            event.route
                        )
                        _eventFlow.emit(RouteEvent.Success(Operation.UPDATED, event.route))
                    } catch (e: InvalidRouteException) {
                        _eventFlow.emit(
                            RouteEvent.Failure(
                                e.message ?: "Failed to update route"
                            )
                        )
                    }
                }
            }
            is AddEditRouteEvent.DeleteRoute -> {
                viewModelScope.launch {
                    try {
                        routesUseCases.deleteRoute(
                            event.route
                        )
                        _eventFlow.emit(RouteEvent.Success(Operation.DELETED, event.route))
                    } catch (e: InvalidRouteException) {
                        _eventFlow.emit(
                            RouteEvent.Failure(
                                e.message ?: "Failed to delete route"
                            )
                        )
                    }
                }
            }
            is AddEditRouteEvent.AddLocation -> {
                viewModelScope.launch {
                    try {
                        routesUseCases.addRouteLocation(
                            event.routeId,
                            event.locationId
                        )
                        _eventFlow.emit(RouteEvent.Success(Operation.RL_ADDED, Route()))
                    } catch (e: InvalidRouteException) {
                        _eventFlow.emit(
                            RouteEvent.Failure(
                                e.message ?: "Failed to add route location"
                            )
                        )
                    }
                }
            }
            is AddEditRouteEvent.DeleteLocation -> {
                viewModelScope.launch {
                    try {
                        routesUseCases.deleteRouteLocation(
                            event.routeId,
                            event.locationId
                        )
                        _eventFlow.emit(RouteEvent.Success(Operation.RL_DELETED, Route()))
                    } catch (e: InvalidRouteException) {
                        _eventFlow.emit(
                            RouteEvent.Failure(
                                e.message ?: "Failed to delete route location"
                            )
                        )
                    }
                }
            }
        }
    }

    fun getRouteWithLocations(id: String) {
        getRoutesWithLocationsJob?.cancel()
        getRoutesWithLocationsJob = routesUseCases.getRoutesWithLocations()
            .onEach { routesWithLocations ->
                val routeWithLocations = routesWithLocations.find { it.route.routeId == id }

                if (routeWithLocations != null && routeWithLocations.route.routeId == id) {
                    _locationsListLiveData.postValue(routeWithLocations.locations)
                    _eventFlow.emit(RouteEvent.Success(Operation.FOUND, routeWithLocations.route))
                } else {
                    _eventFlow.emit(RouteEvent.Failure("Route not found"))
                }
            }
            .launchIn(viewModelScope)
    }

}