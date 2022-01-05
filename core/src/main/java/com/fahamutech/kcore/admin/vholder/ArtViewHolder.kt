package com.fahamutech.kcore.admin.vholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.fahamutech.kcore.R

class ArtViewHolder(val view: View) : RecyclerView.ViewHolder(
    view
) {
    val title: TextView = itemView.findViewById(R.id.article_title)
    val description: TextView = itemView.findViewById(R.id.article_description)
    val image: ImageView = itemView.findViewById(R.id.article_image)
}