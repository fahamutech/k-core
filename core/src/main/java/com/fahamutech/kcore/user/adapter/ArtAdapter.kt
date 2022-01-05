package com.fahamutech.kcore.user.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.fahamutech.kcore.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import android.content.Intent
import android.view.View
import com.fahamutech.kcore.user.activities.ReadActivity
import com.fahamutech.kcore.user.model.Article
import com.fahamutech.kcore.user.vholder.ArtViewHolder

class ArtAdapter(private val articles: List<Article>, private val context: Context) :
    RecyclerView.Adapter<ArtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val inflate =
            LayoutInflater.from(context).inflate(R.layout.article_view_user, parent, false)
        return ArtViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        holder.title.text = articles[position].title
        holder.description.text = articles[position].content
        Glide.with(context)
            .load(articles[position].image)
            .apply(RequestOptions().circleCrop())
            .into(holder.image)
        holder.view.setOnClickListener {
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra("_article", articles[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}