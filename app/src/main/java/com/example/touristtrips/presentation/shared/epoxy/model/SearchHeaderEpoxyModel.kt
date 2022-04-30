package com.example.touristtrips.presentation.shared.epoxy.model

import android.text.TextWatcher
import com.example.touristtrips.R
import com.example.touristtrips.presentation.shared.epoxy.ViewBindingKotlinModel
import com.example.touristtrips.databinding.ModelSearchHeaderBinding

data class SearchHeaderEpoxyModel(
    val textWatcher: TextWatcher
) : ViewBindingKotlinModel<ModelSearchHeaderBinding>(R.layout.model_search_header) {
    override fun ModelSearchHeaderBinding.bind() {
        searchEditText.addTextChangedListener(textWatcher)
    }
}