package com.example.touristtrips.domain.shared.model.route

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.touristtrips.domain.my_locations.model.Location

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