package com.example.touristtrips.feature_route.domain.use_case

import com.example.touristtrips.core.util.route.checkRouteFormatErrors
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.domain.repository.LocalRouteRepository

class UpdateRoute(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(route: Route) {
        checkRouteFormatErrors(route)
        repositoryLocal.updateRoute(route)
    }
}