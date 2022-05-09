package com.example.touristtrips.domain.shared.use_case

import com.example.touristtrips.domain.shared.data.LocationsListTest
import com.example.touristtrips.domain.shared.data.RoutesListTest
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class FindLocationsWithTextTest {

    private lateinit var findLocationsWithText: FindLocationsWithText

    private val listSize = 10000
    private val stringLength = 10

    private val stringToCheck = "Gedimino"

    @Before
    fun setup() {
        findLocationsWithText = FindLocationsWithText()
    }


    @Test
    fun `Routes list of size 10000, search list when none of the routes matches inserted text, time is less than 0,2 sec`() = runBlocking {
        val locationsList = LocationsListTest().getTestLocations(listSize, stringLength)

        val elapsed = measureTimeMillis {
            findLocationsWithText.findLocationsWithText(stringToCheck, locationsList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Routes list of size 10000, search list when all routes matches inserted text, time is less than 0,2 sec`() = runBlocking {
        val locationsList = LocationsListTest(stringToCheck).getTestLocations(listSize, stringLength)

        val elapsed = measureTimeMillis {
            findLocationsWithText.findLocationsWithText(stringToCheck, locationsList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }
}