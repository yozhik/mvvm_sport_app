package com.example.myapplication.data.entity

import com.example.myapplication.data.model.ScheduleEventPojo
import com.example.myapplication.data.model.SportEventPojo
import com.example.myapplication.data.utils.toUnixTimestamp

fun SportEventPojo.toEntity(): SportEventEntity {
    return SportEventEntity(id, title, subtitle, date.toUnixTimestamp(), imageUrl, videoUrl)
}

fun List<SportEventPojo>.toSportEventEntities(): List<SportEventEntity> {
    return this.map { pojo ->
        pojo.toEntity()
    }
}

fun ScheduleEventPojo.toEntity(): ScheduleEventEntity {
    return ScheduleEventEntity(id, title, subtitle, date.toUnixTimestamp(), imageUrl)
}

fun List<ScheduleEventPojo>.toScheduleEventEntities(): List<ScheduleEventEntity> {
    return this.map { pojo ->
        pojo.toEntity()
    }
}

