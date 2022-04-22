package com.example.touristtrips.core.firebase_data.firebase_repository

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.LocationState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class LocationRepository {

    private val database = Firebase.database
    private val locationsReference = database.getReference("locations")

    fun getLocations(liveData: MutableLiveData<LocationState>) {
        locationsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val locations: List<Location> = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Location::class.java)!!
                }
                liveData.value = LocationState(locations as ArrayList<Location>)
            }

            override fun onCancelled(error: DatabaseError) {
                //Nothing
            }

        })
    }

    fun getLocation(liveData: MutableLiveData<LocationState>, locationId: String) {
        locationsReference.child(locationId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val location = snapshot.getValue<Location>()!!
                //Log.i("Snapshot", location.toString())
                liveData.value = LocationState(ArrayList(), location)
            }

            override fun onCancelled(error: DatabaseError) {
                //Nothing
            }

        })
    }

    fun getLocationsWithIds(liveData: MutableLiveData<LocationState>, locationId: List<String>) {
        val locations = ArrayList<Location>()
        locationId.forEach {
            locationsReference.child(it).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val location = snapshot.getValue<Location>()!!
                    //Log.i("Snapshot", location.toString())
                    locations.add(location)
                    liveData.value = LocationState(locations = locations)
                }

                override fun onCancelled(error: DatabaseError) {
                    //Nothing
                }

            })
        }
        //liveData.value = LocationState(locations)
    }

}