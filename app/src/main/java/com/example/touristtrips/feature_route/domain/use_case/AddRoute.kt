package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.core.domain.util.route.checkRouteFormatErrors
import com.example.touristtrips.feature_route.domain.model.InvalidRouteException
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class AddRoute(
    private val repositoryLocal: LocalRouteRepository
) {

    @Throws(InvalidRouteException::class)
    suspend operator fun invoke(route: Route) {
        checkRouteFormatErrors(route)
        repositoryLocal.insertRoute(route)
    }
}