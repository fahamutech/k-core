package com.fahamutech.kcore.user.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.fahamutech.kcore.R
import com.bumptech.glide.Glide
import android.content.Intent
import android.view.View
import com.fahamutech.kcore.user.activities.SimpleImageViewer
import com.fahamutech.kcore.user.model.Testimony
import com.fahamutech.kcore.user.vholder.TestViewHolder

class TestimonyAdapter(private val context: Context, private val testimonies: List<Testimony>) :
    RecyclerView.Adapter<TestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflate = LayoutInflater
            .from(context).inflate(R.layout.testimony_item_user, parent, false)
        return TestViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        Glide.with(context).load(testimonies[position].image).into(holder.imageView)
        holder.imageView.setOnClickListener {
            val intent = Intent(context, SimpleImageViewer::class.java)
            intent.putExtra("_image_", testimonies[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return testimonies.size
    }
}