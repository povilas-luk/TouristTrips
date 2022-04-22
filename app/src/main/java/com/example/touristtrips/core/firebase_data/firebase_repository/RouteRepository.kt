package com.example.touristtrips.core.firebase_data.firebase_repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.core.local_data.local_data_source.RouteWithLocations
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.LocationState
import com.example.touristtrips.feature_online_route.domain.model.RouteLocation
import com.example.touristtrips.feature_online_route.presentation.routes.RouteLocationsState
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.presentation.all_routes_list.RoutesState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class RouteRepository {

    private val database = Firebase.database
    private val routesReference = database.getReference("routes")

    fun getRoutes(liveData: MutableLiveData<RoutesState>) {
        routesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val routes: List<Route> = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Route::class.java)!!
                }

                liveData.value = RoutesState(routes)
            }

            override fun onCancelled(error: DatabaseError) {
                //Nothing
            }

        })
    }

    fun getRoute(liveData: MutableLiveData<Route>, locationsId: ArrayList<String>, routeId: String) {
        routesReference.child(routeId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val route = snapshot.getValue<Route>()!!
                Log.i("Snapshot", route.toString())
                liveData.value = route

                val locId: List<String> = snapshot.child("locationsId").children.map { dataSnapshot ->
                    dataSnapshot.getValue<String>()!!
                }
                locationsId.addAll(locId)
                Log.i("Snapshot", locationsId.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                //Nothing
            }

        })
    }

    fun getRouteWithLocationsId(liveData: MutableLiveData<RouteLocationsState>, routeId: String) {
        routesReference.child(routeId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val route = snapshot.getValue<Route>()!!
                //Log.i("Snapshot", route.toString())

                val locationsId: List<String> = snapshot.child("locationsId").children.map { dataSnapshot ->
                    dataSnapshot.getValue<String>()!!
                }
                liveData.postValue(RouteLocationsState(route, locationsId))
            }

            override fun onCancelled(error: DatabaseError) {
                //Nothing
            }

        })
    }


}