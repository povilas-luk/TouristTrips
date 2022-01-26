package com.example.touristtrips.dependency_injections

import android.app.Application
import androidx.room.Room
import com.example.touristtrips.core.local_data.local_data_source.LocalDatabase
import com.example.touristtrips.core.local_data.local_repository.LocationRepositoryImpl
import com.example.touristtrips.feature_location.domain.repository.LocationRepository
import com.example.touristtrips.feature_location.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            LocalDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(db: LocalDatabase): LocationRepository {
        return LocationRepositoryImpl(db.locationDao)
    }

    @Provides
    @Singleton
    fun provideLocationUseCases(repository: LocationRepository): LocationUseCases {
        return LocationUseCases(
            addLocation = AddLocation(repository),
            getLocations = GetLocations(repository),
            getLocation = GetLocation(repository),
            updateLocation = UpdateLocation(repository),
            deleteLocation = DeleteLocation(repository)
        )
    }
}