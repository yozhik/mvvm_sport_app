package com.example.myapplication.ui.events

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
import com.example.myapplication.ui.model.SportEventUiModel
import com.example.myapplication.ui.model.toUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val _sportEvents = MutableLiveData<UIRequestState<List<SportEventUiModel>>>()
    val sportEvents: LiveData<UIRequestState<List<SportEventUiModel>>> = _sportEvents

    private var job: Job? = null

    fun loadSportEvents() {
        job?.cancel()

        _sportEvents.value = UIRequestState.Loading
        val retrofitApi = RetrofitHelper.getInstance().create(SportEventsApi::class.java)

        job = viewModelScope.launch {
            val repository = SportRepository(RestDataSource(retrofitApi))
            when (val result = repository.loadSportEvents()) {
                is ResultContainer.Success -> _sportEvents.value =
                    UIRequestState.Success(result.data.toUiModel())

                is ResultContainer.Error -> _sportEvents.value = UIRequestState.Error(result.error)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}