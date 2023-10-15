package com.example.myapplication.ui.model

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.BindableItem
import com.example.myapplication.ui.adapter.viewholder.ScheduleEventViewHolder

data class ScheduleEventUiModel(
    val id: String,
    val title: String,
    val date: String,
    val imageUrl: String?,
) : BindableItem {
    override fun bind(holder: RecyclerView.ViewHolder) {
        if (holder is ScheduleEventViewHolder) {
            holder.bind(this)
        }
    }

    override fun areItemsTheSame(other: BindableItem): Boolean {
        if (other is ScheduleEventUiModel) {
            return id == other.id
        }
        return false
    }

    override fun areContentsTheSame(other: BindableItem): Boolean {
        if (other is ScheduleEventUiModel) {
            return this == other
        }
        return false
    }
}

