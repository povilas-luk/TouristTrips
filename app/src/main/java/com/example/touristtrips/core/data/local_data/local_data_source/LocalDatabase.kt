package com.example.touristtrips.core.data.local_data.local_data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_route.domain.model.Route

@Database(entities = [Location::class, Route::class, RouteLocationCrossRef::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "local_db"
    }

    abstract val locationDao: LocationDao
    abstract val routeDao: RouteDao

}