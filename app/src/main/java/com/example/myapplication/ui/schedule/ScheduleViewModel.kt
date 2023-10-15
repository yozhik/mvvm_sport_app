package com.example.myapplication.ui.schedule

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.datasource.RestDataSource
import com.example.myapplication.data.model.ResultContainer
import com.example.myapplication.data.network.RetrofitHelper
import com.example.myapplication.data.network.SportEventsApi
import com.example.myapplication.data.repository.SportRepository
import com.example.myapplication.ui.common.UIRequestState
import com.example.myapplication.ui.model.ScheduleEventUiModel
import com.example.myapplication.ui.model.toScheduleEventUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {

    private val _scheduleEvents = MutableLiveData<UIRequestState<List<ScheduleEventUiModel>>>()
    val scheduleEvents: LiveData<UIRequestState<List<ScheduleEventUiModel>>> = _scheduleEvents
    private val TIMEOUT = 30000L

    private val handler: Handler = Handler()
    private val loadDataTask = object : Runnable {
        override fun run() {
            loadScheduleEvents()
            handler.postDelayed(this, TIMEOUT)
        }
    }

    private var job: Job? = null

    fun loadScheduleEvents() {
        job?.cancel()

        _scheduleEvents.value = UIRequestState.Loading
        val retrofitApi = RetrofitHelper.getInstance().create(SportEventsApi::class.java)

        job = viewModelScope.launch {
            val repository = SportRepository(RestDataSource(retrofitApi))
            when (val result = repository.loadScheduleEvents()) {
                is ResultContainer.Success -> _scheduleEvents.value =
                    UIRequestState.Success(result.data.toScheduleEventUiModel())

                is ResultContainer.Error -> _scheduleEvents.value =
                    UIRequestState.Error(result.error)
            }
        }
    }

    fun startLoadData() {
        handler.post(loadDataTask)
    }

    fun stopLoadData() {
        handler.removeCallbacks(loadDataTask)
    }

    override fun onCleared() {
        super.onCleared()
        stopLoadData()
        job?.cancel()
    }
}