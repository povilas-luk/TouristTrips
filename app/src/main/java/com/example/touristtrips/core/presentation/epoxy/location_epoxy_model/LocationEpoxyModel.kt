package com.example.touristtrips.core.presentation.epoxy.location_epoxy_model

import android.net.Uri
import com.example.touristtrips.R
import com.example.touristtrips.core.presentation.epoxy.ViewBindingKotlinModel
import com.example.touristtrips.databinding.ModelLocationItemBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.squareup.picasso.Picasso

data class LocationEpoxyModel(
    val location: Location,
    val itemSelected: (String) -> Unit
) : ViewBindingKotlinModel<ModelLocationItemBinding>(R.layout.model_location_item) {

    override fun ModelLocationItemBinding.bind() {
        titleTextView.text = location.title
        typeTextView.text = location.type
        cityTextView.text = location.city
        timeToVisitTextView.text = location.months_to_visit
        Picasso.get().load(Uri.parse(location.imageUrl)).placeholder(R.drawable.bruno_soares_284974)
            .into(headerImageView)

        root.setOnClickListener {
            itemSelected(location.locationId)
        }
    }
}
