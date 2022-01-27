package com.example.touristtrips.dependency_injections

import android.app.Application
import androidx.room.Room
import com.example.touristtrips.core.local_data.local_data_source.LocalDatabase
import com.example.touristtrips.core.local_data.local_repository.LocationRepositoryImpl
import com.example.touristtrips.core.local_data.local_repository.RouteRepositoryImpl
import com.example.touristtrips.feature_location.domain.repository.LocationRepository
import com.example.touristtrips.feature_location.domain.use_case.*
import com.example.touristtrips.feature_route.domain.repository.RouteRepository
import com.example.touristtrips.feature_route.domain.use_case.*
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
    fun provideRouteRepository(db: LocalDatabase): RouteRepository {
        return RouteRepositoryImpl(db.routeDao)
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

    @Provides
    @Singleton
    fun provideRouteUseCases(repository: RouteRepository): RoutesUseCases {
        return RoutesUseCases(
            addRoute = AddRoute(repository),
            getRoutes = GetRoutes(repository),
            getRoute = GetRoute(repository),
            updateRoute = UpdateRoute(repository),
            deleteRoute = DeleteRoute(repository),
            addRouteLocation = AddRouteLocation(repository),
            getRouteWithLocations = GetRouteWithLocations(repository),
            getRoutesWithLocations = GetRoutesWithLocations(repository)
        )
    }
}