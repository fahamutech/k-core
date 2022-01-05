package com.fahamutech.kcore.admin.adapter

import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.fahamutech.kcore.R
import com.bumptech.glide.Glide
import android.content.Intent
import android.util.Log
import android.view.View
import com.fahamutech.kcore.admin.database.noSql.CategoryNoSqlDatabase
import com.fahamutech.kcore.admin.database.DataBaseCallback
import com.google.android.material.snackbar.Snackbar
import com.fahamutech.kcore.admin.activities.CategoryContent
import com.fahamutech.kcore.admin.model.Category
import com.fahamutech.kcore.admin.session.Session
import com.fahamutech.kcore.admin.vholder.CatViewHolder

class CatAdapter(private val lists: List<Category>, private val context: Context) :
    RecyclerView.Adapter<CatViewHolder>() {
    private val session: Session = Session(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflate = LayoutInflater.from(context)
            .inflate(R.layout.cat_view, parent, false)
        return CatViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.title.text = lists[position].name
        holder.subtitle.text = lists[position].description
        Glide.with(context).load(lists[position].image).into(holder.imageView)
        holder.view.setOnClickListener {
            val intent = Intent(context, CategoryContent::class.java)
            intent.putExtra("_category", lists[position])
            session.saveLastCategory(lists[position].id)
            session.saveLastTitle(lists[position].name)
            context.startActivity(intent)
        }
        holder.view.setOnLongClickListener { v: View ->
            lists[position].id?.let { deleteDialog(v, it) }
            true
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    private fun deleteDialog(itemView: View, docId: String) {
        //vibrate();
        AlertDialog.Builder(context)
//            .setDescription("Confirm Deletion")
//            .s(Style.HEADER_WITH_ICON)
            .setCancelable(false)
            .setOnDismissListener {
                it.dismiss()
            }
            .setIcon(R.drawable.ic_delete_black_24dp)
            .setPositiveButton("Delete"){d,i->
                //TODO: delete from firestore
                CategoryNoSqlDatabase(itemView.context).deleteCategory(
                    docId,
                    object : DataBaseCallback {
                        override fun then(`object`: Any?) {
                            Log.e(
                                "category delete",
                                "Done delete category"
                            )
                        }
                    },
                    object : DataBaseCallback {
                        override fun then(`object`: Any?) {
                            Log.e(
                                "category delete",
                                "category fail to be deleted"
                            )
                        }
                    })

                //Log.e("TAG******","Umefua");
                d.dismiss()
                Snackbar.make(itemView, "Category deleted...", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel"){d,i->
                d.dismiss()
                Snackbar.make(itemView, "Canceled...", Snackbar.LENGTH_SHORT).show()
            }.show()
    }

}