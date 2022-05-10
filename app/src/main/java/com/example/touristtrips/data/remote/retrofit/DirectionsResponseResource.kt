package com.example.touristtrips.data.remote.retrofit

sealed class DirectionsResponseResource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : DirectionsResponseResource<T>(data, null)
    class Error<T>(message: String) : DirectionsResponseResource<T>(null, message)
}