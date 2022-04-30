package com.example.touristtrips.data.firebase.repository

import androidx.lifecycle.MutableLiveData
import com.example.touristtrips.presentation.shared.locations.location.LocationState
import com.example.touristtrips.domain.my_locations.model.Location
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

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getLocation(liveData: MutableLiveData<LocationState>, locationId: String) {
        locationsReference.child(locationId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val location = snapshot.getValue<Location>()!!
                liveData.value = LocationState(ArrayList(), location)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getLocationsWithIds(liveData: MutableLiveData<LocationState>, locationId: List<String>) {
        val locations = ArrayList<Location>()
        locationId.forEach {
            locationsReference.child(it).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val location = snapshot.getValue<Location>()!!
                    locations.add(location)
                    if (locationId.size == locations.size) {
                        liveData.value = LocationState(locations)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

}