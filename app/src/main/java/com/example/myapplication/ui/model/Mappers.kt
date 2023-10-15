package com.example.myapplication.ui.model

import com.example.myapplication.data.entity.ScheduleEventEntity
import com.example.myapplication.data.entity.SportEventEntity
import com.example.myapplication.data.utils.toFormattedDateString

fun SportEventEntity.toUiModel(): SportEventUiModel {
    return SportEventUiModel(id, title, date.toFormattedDateString(), imageUrl, videoUrl)
}

fun List<SportEventEntity>.toUiModel(): List<SportEventUiModel> {
    return this.map { entity ->
        entity.toUiModel()
    }
}

fun ScheduleEventEntity.toUiModel(): ScheduleEventUiModel {
    return ScheduleEventUiModel(id, title, date.toFormattedDateString(), imageUrl)
}

fun List<ScheduleEventEntity>.toScheduleEventUiModel(): List<ScheduleEventUiModel> {
    return this.map { entity ->
        entity.toUiModel()
    }
}