package com.fahamutech.kcore.admin.vholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.fahamutech.kcore.R

class CatViewHolder(val view: View) : RecyclerView.ViewHolder(
    view
) {
    var imageView: ImageView = itemView.findViewById(R.id.cat_image)
    var title: TextView = itemView.findViewById(R.id.cat_title)
    var subtitle: TextView = itemView.findViewById(R.id.cat_subtitle)
}