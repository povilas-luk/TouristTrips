package com.example.touristtrips.domain.shared.util.location

import android.content.Context
import com.example.touristtrips.R
import com.example.touristtrips.domain.my_locations.model.Location
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class LocalLocations {

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun getLocalLocations(context: Context): List<Location> {
        val textFromJson =
            context.resources.openRawResource(R.raw.vilnius_locations).bufferedReader()
                .use { it.readText() }

        val adapter: JsonAdapter<LocationsResponse> = moshi.adapter(LocationsResponse::class.java)
        return adapter.fromJson(textFromJson)!!.locations

    }
}