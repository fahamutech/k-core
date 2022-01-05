package com.fahamutech.kcore.user.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.fahamutech.kcore.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import android.view.View
import com.fahamutech.kcore.user.activities.CategoryContent
import com.fahamutech.kcore.user.model.Category
import com.fahamutech.kcore.user.session.Session
import com.fahamutech.kcore.user.vholder.CatViewHolder

class CatAdapter(private val lists: List<Category>, private val context: Context) :
    RecyclerView.Adapter<CatViewHolder>() {
    private val session: Session
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflate = LayoutInflater.from(context)
            .inflate(R.layout.cat_view_user, parent, false)
        return CatViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.title.text = lists[position].name
        holder.subtitle.text = lists[position].description
        Glide.with(context).load(lists[position].image).into(holder.imageView)
        holder.view.setOnClickListener { v: View? ->
            Snackbar.make(v!!, "Itemc clicked is : $position", Snackbar.LENGTH_SHORT).show()
            val intent = Intent(context, CategoryContent::class.java)
            intent.putExtra("_category", lists[position])
            session.saveLastCategory(lists[position].id)
            session.saveLastTitle(lists[position].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    init {
        session = Session(context)
    }
}