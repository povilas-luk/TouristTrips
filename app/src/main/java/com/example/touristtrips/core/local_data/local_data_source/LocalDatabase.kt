package com.example.touristtrips.core.local_data.local_data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.touristtrips.feature_location.domain.model.Location

@Database(entities = [Location::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        private var localDatabase: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            if (localDatabase != null) {
                return localDatabase!!
            }

            localDatabase = Room
                .databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local-database"
                ).build()
            return localDatabase!!
        }
    }
}