package com.example.touristtrips.core.presentation.epoxy.routes_epoxy

import android.net.Uri
import com.example.touristtrips.R
import com.example.touristtrips.core.presentation.epoxy.ViewBindingKotlinModel
import com.example.touristtrips.databinding.ModelRouteItemBinding
import com.example.touristtrips.feature_route.domain.model.Route
import com.squareup.picasso.Picasso

data class RoutesEpoxyModel(
    val route: Route,
    val itemSelected: (String) -> Unit
) : ViewBindingKotlinModel<ModelRouteItemBinding>(R.layout.model_route_item) {

    override fun ModelRouteItemBinding.bind() {
        titleTextView.text = route.title
        typeTextView.text = route.type
        cityTextView.text = route.city
        timeToVisitTextView.text = route.months_to_visit
        Picasso.get().load(Uri.parse(route.imageUrl)).placeholder(R.drawable.bruno_soares_284974)
            .into(headerImageView)

        root.setOnClickListener {
            itemSelected(route.routeId)
        }
    }
}
