package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.RestDataSource
import com.example.myapplication.data.entity.ScheduleEventEntity
import com.example.myapplication.data.entity.SportEventEntity
import com.example.myapplication.data.model.ResultContainer
import com.example.myapplication.data.model.ScheduleEventPojo
import com.example.myapplication.data.model.SportEventPojo
import com.example.myapplication.data.network.SportEventsApi
import com.example.myapplication.data.utils.CalendarUtils
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class SportRepositoryTest {

    @Mock
    private lateinit var calendarUtils: CalendarUtils


    private lateinit var sportRepository: SportRepository

    private val sportEventsApiMock: SportEventsApi = mock(SportEventsApi::class.java)

    @Before
    fun setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this)

        // Create a RestDataSource using the mock SportEventsApi
        val dataSource = RestDataSource(sportEventsApiMock)

        // Create the SportRepository with the dataSource
        sportRepository = SportRepository(dataSource)
    }

    @Test
    fun loadSportEvents_Success() = runBlocking {
        val sportEventPojos = arrayListOf(
            SportEventPojo(
                "1",
                "Event 1",
                "Subtitle 1",
                "2023-10-15T01:48:20.915Z",
                "Image 1",
                "Video 1"
            ),
            SportEventPojo(
                "2",
                "Event 2",
                "Subtitle 2",
                "2023-10-15T02:48:20.915Z",
                "Image 2",
                "Video 2"
            )
        )
        val expectedEntities = arrayListOf(
            SportEventEntity("1", "Event 1", "Subtitle 1", 1697327300915, "Image 1", "Video 1"),
            SportEventEntity("2", "Event 2", "Subtitle 2", 1697330900915, "Image 2", "Video 2")
        )

        `when`(sportEventsApiMock.getEvents()).thenReturn(Response.success(sportEventPojos))

        val result = sportRepository.loadSportEvents()

        assert(result is ResultContainer.Success)
        val successResult = result as ResultContainer.Success
        assertEquals(successResult.data, expectedEntities)
    }

    @Test
    fun loadScheduleEvents_Success() = runBlocking {
        val scheduleEventPojos = arrayListOf(
            ScheduleEventPojo(
                "1",
                "Event 1",
                "Subtitle 1",
                "2023-10-15T01:48:20.915Z",
                "Image 1",
            ),
            ScheduleEventPojo(
                "2",
                "Event 2",
                "Subtitle 2",
                "2023-10-15T02:48:20.915Z",
                "Image 2",
            )
        )
        val expectedEntities = arrayListOf(
            ScheduleEventEntity("1", "Event 1", "Subtitle 1", 1697327300915, "Image 1"),
            ScheduleEventEntity("2", "Event 2", "Subtitle 2", 1697330900915, "Image 2")
        )

        `when`(sportEventsApiMock.getSchedule()).thenReturn(Response.success(scheduleEventPojos))

        `when`(calendarUtils.getTodayStartEndTimeInMillis()).thenReturn(
            Pair(1634236800000L, 1634323199000L)
        )

        val result = sportRepository.loadScheduleEvents()

        assert(result is ResultContainer.Success)
        val successResult = result as ResultContainer.Success
        assertEquals(successResult.data, expectedEntities)
    }
}
