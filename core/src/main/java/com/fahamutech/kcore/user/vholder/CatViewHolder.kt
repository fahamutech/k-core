package com.fahamutech.kcore.user.vholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.fahamutech.kcore.R

class CatViewHolder(val view: View) : RecyclerView.ViewHolder(
    view
) {
    var imageView: ImageView
    var title: TextView
    var subtitle: TextView

    init {
        imageView = itemView.findViewById(R.id.cat_image)
        title = itemView.findViewById(R.id.cat_title)
        subtitle = itemView.findViewById(R.id.cat_subtitle)
    }
}