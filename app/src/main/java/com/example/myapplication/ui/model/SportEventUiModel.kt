package com.example.myapplication.ui.model

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.BindableItem
import com.example.myapplication.ui.adapter.viewholder.SportEventViewHolder

data class SportEventUiModel(
    val id: String,
    val title: String,
    val date: String,
    val imageUrl: String?,
    val videoUrl: String?,
) : BindableItem {
    override fun bind(holder: RecyclerView.ViewHolder) {
        if (holder is SportEventViewHolder) {
            holder.bind(this)
        }
    }

    override fun areItemsTheSame(other: BindableItem): Boolean {
        if (other is SportEventUiModel) {
            return id == other.id
        }
        return false
    }

    override fun areContentsTheSame(other: BindableItem): Boolean {
        if (other is SportEventUiModel) {
            return this == other
        }
        return false
    }
}
