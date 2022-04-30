package com.example.touristtrips.domain.shared.util

sealed class SortType {
    object Ascending : SortType()
    object Descending : SortType()
}