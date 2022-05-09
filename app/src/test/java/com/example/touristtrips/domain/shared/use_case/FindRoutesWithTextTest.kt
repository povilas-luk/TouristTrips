package com.example.touristtrips.domain.shared.use_case

import com.example.touristtrips.domain.shared.data.RoutesListTest
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class FindRoutesWithTextTest {

    private lateinit var findRoutesWithText: FindRoutesWithText

    private val listSize = 10000
    private val stringLength = 10

    private val stringToCheck = "Vilniuje"

    @Before
    fun setup() {
        findRoutesWithText = FindRoutesWithText()
    }

    @Test
    fun `Routes list of size 10000, search list when none of the routes matches inserted text, time is less than 0,2 sec`() = runBlocking {
        val routesList = RoutesListTest().getTestRoutes(listSize, stringLength)

        val elapsed = measureTimeMillis {
            FindRoutesWithText().findRoutesWithText(stringToCheck, routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Routes list of size 10000, search list when all routes matches inserted text, time is less than 0,2 sec`() = runBlocking {
        val routesList = RoutesListTest(stringToCheck).getTestRoutes(listSize, stringLength)

        val elapsed = measureTimeMillis {
            FindRoutesWithText().findRoutesWithText(stringToCheck, routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }
}

