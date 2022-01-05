package com.fahamutech.kcore.user.vholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.fahamutech.kcore.R

class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView

    init {
        imageView = itemView.findViewById(R.id.testimony_image)
    }
}