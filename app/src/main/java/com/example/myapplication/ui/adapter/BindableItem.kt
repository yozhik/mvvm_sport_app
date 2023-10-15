package com.example.myapplication.ui.adapter

import androidx.recyclerview.widget.RecyclerView

interface BindableItem {
    fun bind(holder: RecyclerView.ViewHolder)
    fun areItemsTheSame(other: BindableItem): Boolean
    fun areContentsTheSame(other: BindableItem): Boolean
}