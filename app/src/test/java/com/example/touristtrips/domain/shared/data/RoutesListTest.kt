package com.example.touristtrips.domain.shared.data

import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.my_routes.model.Route
import java.util.*
import kotlin.collections.ArrayList

class RoutesListTest (
    val stringToInsert: String? = null
) {

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun getTestRoutes(listSize: Int, stringLength: Int): List<Route> {
        val routesList: ArrayList<Route> = ArrayList()

        for (i in 1..listSize) {
            var routeString = stringToInsert
            if (routeString.isNullOrEmpty()) {
                routeString = getRandomString(stringLength)
            }
            routesList.add(
                Route(
                routeId = UUID.randomUUID().toString(),
                type = routeString,
                title = routeString,
                description = "Lukiškių kalėjimas buvo vienas moderniausių visoje Rusų imperijoje, jame kaliniai atliko bausmę, dirbo, meldėsi ir gavo sveikatos priežiūros paslaugas. Nuteistieji čia buvo kalinami daugiau nei šimtmetį – per abu Pasaulinius karus, sovietmečiu ir Lietuvai atgavus nepriklausomybę. Paskutiniai kaliniai iš šio kalėjimo buvo iškelti tik 2019 metais. Įdomu tai, kad Lukiškių kalėjimas iki šiol išsaugojo originalius interjero ir eksterjero elementus – plyteles ir dekoracijas, tad ekskursijos metu atkreipkite dėmesį į šį šimtmečio paveldą.\\n\\nŠiandien kalėjimas įgavo naują dvasią ir kviečia lankytojus atrasti šią paslaptingą teritoriją bei pajusti jos išskirtinę atmosferą. Leiskitės į dienos ar nakties ekskursijas, pamatykite kalinių kameras, išgirskite įdomiausias istorijas ir būkite pasiruošę naujoms patirtims.", createdAt = System.currentTimeMillis(),
                imageUrl = "https://www.lithuania.travel/ckeditor/jsplus_image_editor/files/2b8cba3203c3f7ccb00ac7a95fbecebc817bb8be.jpeg",
                city = routeString,
                months_to_visit = routeString,
                price = 6.0F,
            )
            )
        }
        return routesList
    }

    fun getRandomString(stringLength: Int): String {
        return (1..stringLength)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
    }
}