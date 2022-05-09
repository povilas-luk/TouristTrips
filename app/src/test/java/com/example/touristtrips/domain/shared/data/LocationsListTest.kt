package com.example.touristtrips.domain.shared.data

import android.util.Log
import com.example.touristtrips.domain.my_locations.model.Location
import java.util.*
import kotlin.collections.ArrayList

class LocationsListTest (
    val stringToInsert: String? = null
) {

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun getTestLocations(listSize: Int, stringLength: Int): List<Location> {
        val locationList: ArrayList<Location> = ArrayList()

        for (i in 1..listSize) {
            var locationString = stringToInsert
            if (locationString.isNullOrEmpty()) {
                locationString = getRandomString(stringLength)
            }
            locationList.add(Location(
                locationId = UUID.randomUUID().toString(),
                type = locationString,
                title = locationString,
                description = "Lukiškių kalėjimas buvo vienas moderniausių visoje Rusų imperijoje, jame kaliniai atliko bausmę, dirbo, meldėsi ir gavo sveikatos priežiūros paslaugas. Nuteistieji čia buvo kalinami daugiau nei šimtmetį – per abu Pasaulinius karus, sovietmečiu ir Lietuvai atgavus nepriklausomybę. Paskutiniai kaliniai iš šio kalėjimo buvo iškelti tik 2019 metais. Įdomu tai, kad Lukiškių kalėjimas iki šiol išsaugojo originalius interjero ir eksterjero elementus – plyteles ir dekoracijas, tad ekskursijos metu atkreipkite dėmesį į šį šimtmečio paveldą.\\n\\nŠiandien kalėjimas įgavo naują dvasią ir kviečia lankytojus atrasti šią paslaptingą teritoriją bei pajusti jos išskirtinę atmosferą. Leiskitės į dienos ar nakties ekskursijas, pamatykite kalinių kameras, išgirskite įdomiausias istorijas ir būkite pasiruošę naujoms patirtims.",
                latitude = "54.69180559315594",
                longitude = "25.267835687452532",
                city = locationString,
                createdAt = 1643219159778,
                imageUrl = "https://www.lithuania.travel/ckeditor/jsplus_image_editor/files/2b8cba3203c3f7ccb00ac7a95fbecebc817bb8be.jpeg",
                months_to_visit = locationString,
                price = 6.0F,
                location_search = "Search string",
            )
            )
        }
        return locationList
    }

    fun getRandomString(stringLength: Int): String {
        return (1..stringLength)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
    }


}

