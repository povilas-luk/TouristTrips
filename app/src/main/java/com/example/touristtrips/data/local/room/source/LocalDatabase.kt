package com.example.touristtrips.data.local.room.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.model.route.Route
import com.example.touristtrips.domain.shared.model.route.RouteLocationCrossRef

@Database(entities = [Location::class, Route::class, RouteLocationCrossRef::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "local_db"
    }

    abstract val locationDao: LocationDao
    abstract val routeDao: RouteDao

}