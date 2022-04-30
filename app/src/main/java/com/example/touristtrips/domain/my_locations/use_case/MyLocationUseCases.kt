package com.example.touristtrips.domain.my_locations.use_case

class MyLocationUseCases(
    val addLocation: AddLocation,
    val getLocations: GetLocations,
    val getLocation: GetLocation,
    val updateLocation: UpdateLocation,
    val deleteLocation: DeleteLocation
)