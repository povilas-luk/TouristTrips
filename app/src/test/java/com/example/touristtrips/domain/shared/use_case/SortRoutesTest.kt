package com.example.touristtrips.domain.shared.use_case

import com.example.touristtrips.domain.shared.model.route.Route
import com.example.touristtrips.domain.shared.data.RoutesListTest
import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.domain.shared.util.SortType
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class SortRoutesTest {

    private lateinit var sortRoutes: SortRoutes
    private lateinit var routesList: List<Route>

    private val listSize = 10000
    private val stringLength = 10

    @Before
    fun setup() {
        sortRoutes = SortRoutes()
        routesList = RoutesListTest().getTestRoutes(listSize, stringLength)
    }

    @Test
    fun `Routes list of size 10000, sorting by title descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.Title(SortType.Descending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Routes list of size 10000, sorting by title ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.Title(SortType.Ascending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }


    @Test
    fun `Routes list of size 10000, sorting by city descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.City(SortType.Descending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Routes list of size 10000, sorting by city ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.City(SortType.Ascending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }


    @Test
    fun `Routes list of size 10000, sorting by time to visit descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.TimeToVisit(SortType.Descending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Routes list of size 10000, sorting by time to visit ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.TimeToVisit(SortType.Ascending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }


    @Test
    fun `Routes list of size 10000, sorting by type descending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.Type(SortType.Descending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }

    @Test
    fun `Routes list of size 10000, sorting by type ascending, time is less than 0,2 sec`() = runBlocking {
        val elapsed = measureTimeMillis {
            sortRoutes.sortRoutes(SortOrder.Type(SortType.Ascending), routesList)
        }
        Truth.assertThat(elapsed).isLessThan(200)
    }

}