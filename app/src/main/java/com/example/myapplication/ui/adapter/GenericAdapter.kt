package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class GenericAdapter<T : BindableItem, VH : RecyclerView.ViewHolder>(
    private val onClick: (T) -> Unit,
    private val viewHolderCreator: (View, (T) -> Unit) -> VH
) : RecyclerView.Adapter<VH>() {

    private var data: List<T> = emptyList()

    fun setData(newData: List<T>) {
        val diffResult = DiffUtil.calculateDiff(GenericDiffCallback(data, newData))
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_sport_event_item, parent, false)
        return viewHolderCreator(view, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = data[position]
        bindViewHolder(holder, item)
    }

    override fun getItemCount() = data.size

    private fun bindViewHolder(holder: VH, item: T) {
        item.bind(holder)
    }
}
