package com.example.touristtrips.feature_route.presentation.route_locations_epoxy

import android.net.Uri
import com.example.touristtrips.R
import com.example.touristtrips.core.Epoxy.ViewBindingKotlinModel
import com.example.touristtrips.databinding.ModelLocationItemBinding
import com.example.touristtrips.databinding.ModelRouteLocationItemBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.squareup.picasso.Picasso

class RouteLocationsEpoxyModel(
    val location: Location,
    val itemSelected: (String) -> Unit,
    val deleteItemSelected: (String) -> Unit
) : ViewBindingKotlinModel<ModelRouteLocationItemBinding>(R.layout.model_route_location_item) {

    override fun ModelRouteLocationItemBinding.bind() {
        titleTextView.text = location.title
        typeTextView.text = location.type
        cityTextView.text = location.city
        timeToVisitTextView.text = location.months_to_visit
        Picasso.get().load(Uri.parse(location.imageUrl)).placeholder(R.drawable.bruno_soares_284974).into(headerImageView)

        root.setOnClickListener {
            itemSelected(location.locationId)
        }

        deleteLocationImageView.setOnClickListener {
            deleteItemSelected(location.locationId)
        }
    }
}