package com.example.touristtrips.domain.shared.use_case

import com.google.common.truth.Truth.assertThat
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.domain.shared.data.LocationsListTest
import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.domain.shared.util.SortType
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import kotlin.system.measureTimeMillis

class SortLocationsTest {

    private lateinit var sortLocations: SortLocations
    private lateinit var locationsList: List<Location>

    private val listSize = 10000
    private val stringLength = 10

    @Before
    fun setup() {
        sortLocations = SortLocations()
        locationsList = LocationsListTest().getTestLocations(listSize, stringLength)
    }

    @Test
    fun `Locations list of size 10000, sorting by title descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.Title(SortType.Descending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Locations list of size 10000, sorting by title ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.Title(SortType.Ascending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }


    @Test
    fun `Locations list of size 10000, sorting by city descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.City(SortType.Descending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Locations list of size 10000, sorting by city ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.City(SortType.Ascending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }


    @Test
    fun `Locations list of size 10000, sorting by time to visit descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.TimeToVisit(SortType.Descending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Locations list of size 10000, sorting by time to visit ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.TimeToVisit(SortType.Ascending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }


    @Test
    fun `Locations list of size 10000, sorting by type descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.Type(SortType.Descending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Locations list of size 10000, sorting by type ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortLocations.sortLocations(SortOrder.Type(SortType.Ascending), locationsList)
        }
        assertThat(elapsed).isLessThan(200)
    }


}