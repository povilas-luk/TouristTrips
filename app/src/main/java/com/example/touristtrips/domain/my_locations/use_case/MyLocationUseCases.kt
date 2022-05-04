package com.example.touristtrips.domain.my_locations.use_case

import com.example.touristtrips.domain.shared.use_case.FindLocationsWithText
import com.example.touristtrips.domain.shared.use_case.SortLocations

class MyLocationUseCases(
    val addLocation: AddLocation,
    val getLocations: GetLocations,
    val getLocation: GetLocation,
    val updateLocation: UpdateLocation,
    val deleteLocation: DeleteLocation,
    val sortLocations: SortLocations,
    val findLocationsWithText: FindLocationsWithText,
)