package com.fahamutech.kcore.user.vholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.fahamutech.kcore.R

class ArtViewHolder(val view: View) : RecyclerView.ViewHolder(
    view
) {
    val title: TextView
    val description: TextView
    val image: ImageView

    init {
        title = itemView.findViewById(R.id.article_title)
        description = itemView.findViewById(R.id.article_description)
        image = itemView.findViewById(R.id.article_image)
    }
}