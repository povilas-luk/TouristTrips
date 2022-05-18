package com.example.touristtrips.domain.my_routes.use_case

import com.example.touristtrips.domain.shared.util.route.checkRouteFormatErrors
import com.example.touristtrips.domain.shared.model.route.Route
import com.example.touristtrips.domain.my_routes.repository.LocalRouteRepository

class UpdateRoute(
    private val repositoryLocal: LocalRouteRepository
) {
    suspend operator fun invoke(route: Route) {
        checkRouteFormatErrors(route)
        repositoryLocal.updateRoute(route)
    }
}