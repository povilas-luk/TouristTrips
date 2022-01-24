package com.example.touristtrips.core.local_data.local_data_source

import androidx.room.*
import com.example.touristtrips.feature_location.domain.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM Location")
    fun getAllLocations(): Flow<List<Location>>

    @Query("SELECT * FROM Location WHERE id = :id")
    suspend fun getLocationById(id: String) : Location?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    @Update
    suspend fun updateLocation(location: Location)
}