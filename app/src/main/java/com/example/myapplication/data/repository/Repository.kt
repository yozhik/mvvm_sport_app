package com.example.myapplication.data.repository

import com.example.myapplication.data.entity.ScheduleEventEntity
import com.example.myapplication.data.entity.SportEventEntity
import com.example.myapplication.data.model.ResultContainer

interface Repository {
    suspend fun loadSportEvents(): ResultContainer<List<SportEventEntity>>
    suspend fun loadScheduleEvents(): ResultContainer<List<ScheduleEventEntity>>
}