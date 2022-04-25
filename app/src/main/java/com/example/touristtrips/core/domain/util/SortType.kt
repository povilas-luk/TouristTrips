package com.example.touristtrips.core.domain.util

sealed class SortType {
    object Ascending : SortType()
    object Descending : SortType()
}