package com.example.touristtrips.data.local.room.source

import androidx.room.*
import com.example.touristtrips.domain.my_locations.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM location_entity")
    fun getAllLocations(): Flow<List<Location>>

    @Query("SELECT * FROM location_entity WHERE locationId = :id")
    suspend fun getLocationById(id: String): Location?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    @Update
    suspend fun updateLocation(location: Location)
}