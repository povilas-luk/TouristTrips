package com.example.touristtrips.domain.shared.model

sealed class SortType {
    object Ascending : SortType()
    object Descending : SortType()
}