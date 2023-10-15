package com.example.myapplication.data.datasource

import com.example.myapplication.data.model.ResultContainer
import com.example.myapplication.data.model.ScheduleEventPojo
import com.example.myapplication.data.model.SportEventPojo

interface DataSource {
    suspend fun loadSportEvents(): ResultContainer<List<SportEventPojo>>
    suspend fun loadScheduleEvents(): ResultContainer<List<ScheduleEventPojo>>
}