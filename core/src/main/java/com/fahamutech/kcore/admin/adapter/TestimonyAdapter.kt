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
import com.fahamutech.kcore.admin.database.noSql.TestmonyNoSqlDatabase
import com.fahamutech.kcore.admin.database.DataBaseCallback
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import com.fahamutech.kcore.admin.activities.SimpleImageViewer
import com.fahamutech.kcore.admin.model.Testimony
import com.fahamutech.kcore.admin.vholder.TestViewHolder

class TestimonyAdapter(private val context: Context, private val testimonies: List<Testimony>) :
    RecyclerView.Adapter<TestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val inflate = LayoutInflater
            .from(context).inflate(R.layout.testimony_item, parent, false)
        return TestViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        Glide.with(context).load(testimonies[position].image).into(holder.imageView)
        holder.imageView.setOnClickListener {
            val intent = Intent(context, SimpleImageViewer::class.java)
            intent.putExtra("_image_", testimonies[position])
            context.startActivity(intent)
        }
        holder.imageView.setOnLongClickListener { v: View ->
            testimonies[position].id?.let { deleteDialog(v, it) }
            true
        }
    }

    override fun getItemCount(): Int {
        return testimonies.size
    }

    private fun deleteDialog(itemView: View, docId: String) {
        //vibrate();
        AlertDialog.Builder(context)
//            .setDescription("Confirm Deletion")
//            .setStyle(Style.HEADER_WITH_ICON)
            .setCancelable(false)
            .setOnDismissListener {
                it.dismiss()
            }
            .setIcon(R.drawable.ic_delete_black_24dp)
            .setPositiveButton("Delete") { d, _ ->
                //TODO: delete from firestore
                TestmonyNoSqlDatabase(itemView.context).deleteTestimony(
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
            .setNegativeButton("Cancel") { d, _ ->
                d.dismiss()
                Snackbar.make(itemView, "Canceled...", Snackbar.LENGTH_SHORT).show()
            }.show()
    }
}