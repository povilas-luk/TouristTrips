package com.example.touristtrips.presentation.my_routes.route_locations_epoxy

import android.net.Uri
import androidx.core.view.isVisible
import com.example.touristtrips.R
import com.example.touristtrips.presentation.shared.epoxy.ViewBindingKotlinModel
import com.example.touristtrips.databinding.ModelRouteLocationItemBinding
import com.example.touristtrips.domain.my_locations.model.Location
import com.squareup.picasso.Picasso

class RouteLocationsEpoxyModel(
    val location: Location,
    val itemSelected: (String) -> Unit,
    val deleteItemSelected: (String) -> Unit,
    val sequenceItemSelected: (String) -> Unit,
    val deleteButtonIsActive: Boolean = true
) : ViewBindingKotlinModel<ModelRouteLocationItemBinding>(R.layout.model_route_location_item) {

    override fun ModelRouteLocationItemBinding.bind() {
        titleTextView.text = location.title
        typeTextView.text = location.type
        cityTextView.text = location.city
        timeToVisitTextView.text = location.months_to_visit
        Picasso.get().load(Uri.parse(location.imageUrl)).placeholder(R.drawable.bruno_soares_284974)
            .into(headerImageView)

        root.setOnClickListener {
            itemSelected(location.locationId)
        }

        if (deleteButtonIsActive) {
            deleteLocationImageView.isVisible = true
            deleteLocationImageView.setOnClickListener {
                deleteItemSelected(location.locationId)
            }
            locationSequenceImageView.setOnClickListener {
                sequenceItemSelected(location.locationId)
            }
        } else {
            deleteLocationImageView.isVisible = false
        }

    }
}