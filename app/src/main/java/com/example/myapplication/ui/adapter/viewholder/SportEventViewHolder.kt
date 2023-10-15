package com.example.myapplication.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.ui.model.SportEventUiModel

class SportEventViewHolder(
    itemView: View,
    private val onClick: (SportEventUiModel) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.name)
    private val date: TextView = itemView.findViewById(R.id.date)
    private val image: ImageView = itemView.findViewById(R.id.image)

    fun bind(sportEvent: SportEventUiModel) {
        title.text = sportEvent.title
        date.text = sportEvent.date
        if (sportEvent.imageUrl != null) {
            Glide.with(image.context)
                .load(sportEvent.imageUrl)
                .placeholder(R.drawable.no_image_placeholder)
                .error(R.drawable.no_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(image)
        }

        itemView.setOnClickListener {
            onClick(sportEvent)
        }
    }
}