package com.example.touristtrips.core.local_data.local_data_source

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_route.domain.model.Route

data class RouteWithLocations(
    @Embedded
    val route: Route,

    @Relation(
        parentColumn = "routeId",
        entityColumn = "locationId",
        associateBy = Junction(
            RouteLocationCrossRef::class,
        )
    )
    val locations: List<Location>
)