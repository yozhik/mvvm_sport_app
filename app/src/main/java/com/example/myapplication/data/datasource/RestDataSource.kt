package com.example.myapplication.data.datasource

import com.example.myapplication.data.model.ResultContainer
import com.example.myapplication.data.model.ScheduleEventPojo
import com.example.myapplication.data.model.SportEventPojo
import com.example.myapplication.data.network.SportEventsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RestDataSource(private val api: SportEventsApi) : DataSource {
    override suspend fun loadSportEvents(): ResultContainer<List<SportEventPojo>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getEvents()

                if (result.isSuccessful) {
                    val rawData = result.body()
                    if (rawData != null) {
                        ResultContainer.Success(rawData)
                    } else {
                        ResultContainer.Error("Received empty response from the server")
                    }
                } else {
                    ResultContainer.Error(result.errorBody()?.string() ?: "Unknown error occurred")
                }
            } catch (e: Exception) {
                ResultContainer.Error("An error occurred: ${e.message}")
            }
        }
    }

    override suspend fun loadScheduleEvents(): ResultContainer<List<ScheduleEventPojo>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getSchedule()

                if (result.isSuccessful) {
                    val rawData = result.body()
                    if (rawData != null) {
                        ResultContainer.Success(rawData)
                    } else {
                        ResultContainer.Error("Received empty response from the server")
                    }
                } else {
                    ResultContainer.Error(result.errorBody()?.string() ?: "Unknown error occurred")
                }
            } catch (e: Exception) {
                ResultContainer.Error("An error occurred: ${e.message}")
            }
        }
    }
}