package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.DataSource
import com.example.myapplication.data.entity.ScheduleEventEntity
import com.example.myapplication.data.entity.SportEventEntity
import com.example.myapplication.data.entity.toScheduleEventEntities
import com.example.myapplication.data.entity.toSportEventEntities
import com.example.myapplication.data.model.ResultContainer
import com.example.myapplication.data.utils.CalendarUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SportRepository(private val dataSource: DataSource) : Repository {
    override suspend fun loadSportEvents(): ResultContainer<List<SportEventEntity>> {
        return withContext(Dispatchers.IO) {
            when (val data = dataSource.loadSportEvents()) {
                is ResultContainer.Error -> data
                is ResultContainer.Success -> ResultContainer.Success(
                    data.data
                        .toSportEventEntities()
                        .sortedBy { it.date }
                )
            }
        }
    }

    override suspend fun loadScheduleEvents(): ResultContainer<List<ScheduleEventEntity>> {
        return withContext(Dispatchers.IO) {
            val data = dataSource.loadScheduleEvents()
            val startEndOfToday = CalendarUtils().getTodayStartEndTimeInMillis()
            when (data) {
                is ResultContainer.Error -> data
                is ResultContainer.Success -> ResultContainer.Success(
                    data.data
                        .toScheduleEventEntities()
                        //TODO: uncomment this code when server will be returning elements with date
                        // other than TODAY, because for now it will show user empty screen!
//                        .filter { scheduleEvent ->
//                            scheduleEvent.date >= startEndOfToday.first
//                                    && scheduleEvent.date <= startEndOfToday.second
//                        }
                        .sortedBy { it.date }
                )
            }
        }
    }
}