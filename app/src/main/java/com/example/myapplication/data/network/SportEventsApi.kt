package com.example.myapplication.data.network

import com.example.myapplication.data.model.ScheduleEventPojo
import com.example.myapplication.data.model.SportEventPojo
import retrofit2.Response
import retrofit2.http.GET

interface SportEventsApi {
    @GET("/getSchedule")
    suspend fun getSchedule(): Response<ArrayList<ScheduleEventPojo>>

    @GET("/getEvents")
    suspend fun getEvents(): Response<ArrayList<SportEventPojo>>
}