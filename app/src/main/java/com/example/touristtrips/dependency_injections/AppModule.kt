package com.example.touristtrips.dependency_injections

import android.app.Application
import androidx.room.Room
import com.example.touristtrips.core.domain.util.Constants
import com.example.touristtrips.core.data.local_data.local_data_source.LocalDatabase
import com.example.touristtrips.core.data.local_data.local_repository.LocalLocationRepositoryImpl
import com.example.touristtrips.core.data.local_data.local_repository.LocalRouteRepositoryImpl
import com.example.touristtrips.core.retrofit_maps.DirectionsApi
import com.example.touristtrips.core.retrofit_maps.DirectionsRepositoryImpl
import com.example.touristtrips.feature_location.domain.repository.LocalLocationRepository
import com.example.touristtrips.feature_location.domain.use_case.*
import com.example.touristtrips.core.domain.repository.DirectionsRepository
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository
import com.example.touristtrips.feature_route.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    fun provideDirectionsServices(): DirectionsApi = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(DirectionsApi::class.java)

    @Provides
    @Singleton
    fun provideLocationRepository(db: LocalDatabase): LocalLocationRepository {
        return LocalLocationRepositoryImpl(db.locationDao)
    }

    @Provides
    @Singleton
    fun provideRouteRepository(db: LocalDatabase): LocalRouteRepository {
        return LocalRouteRepositoryImpl(db.routeDao)
    }

    @Provides
    @Singleton
    fun provideDirectionsRepository(directionsApi: DirectionsApi): DirectionsRepository = DirectionsRepositoryImpl(directionsApi)

    @Provides
    @Singleton
    fun provideMyLocationUseCases(repositoryLocal: LocalLocationRepository): MyLocationUseCases {
        return MyLocationUseCases(
            addLocation = AddLocation(repositoryLocal),
            getLocations = GetLocations(repositoryLocal),
            getLocation = GetLocation(repositoryLocal),
            updateLocation = UpdateLocation(repositoryLocal),
            deleteLocation = DeleteLocation(repositoryLocal)
        )
    }

    @Provides
    @Singleton
    fun provideRouteUseCases(repositoryLocal: LocalRouteRepository): RoutesUseCases {
        return RoutesUseCases(
            addRoute = AddRoute(repositoryLocal),
            getRoutes = GetRoutes(repositoryLocal),
            getRoute = GetRoute(repositoryLocal),
            updateRoute = UpdateRoute(repositoryLocal),
            deleteRoute = DeleteRoute(repositoryLocal),
            addRouteLocation = AddRouteLocation(repositoryLocal),
            getRouteWithLocations = GetRouteWithLocations(repositoryLocal),
            getRoutesWithLocations = GetRoutesWithLocations(repositoryLocal),
            deleteRouteLocation = DeleteRouteLocation(repositoryLocal)
        )
    }

}