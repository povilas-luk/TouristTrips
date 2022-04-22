package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.feature_location.domain.model.InvalidLocationException
import com.example.touristtrips.feature_route.domain.model.InvalidRouteException
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class AddRoute(
    private val repositoryLocal: LocalRouteRepository
) {

    @Throws(InvalidRouteException::class)
    suspend operator fun invoke(route: Route) {
        if (route.type.isBlank()) {
            throw InvalidLocationException("The type of the location can't be empty.")
        }
        if (route.title.isBlank()) {
            throw InvalidLocationException("The title of the location can't be empty.")
        }
        if (route.description.isBlank()) {
            throw InvalidLocationException("The content of the location can't be empty.")
        }
        if (route.imageUrl.isBlank()) {
            throw InvalidLocationException("The image of the location can't be empty.")
        }

        repositoryLocal.insertRoute(route)
    }
}