package com.fahamutech.kcore.user.database.noSql

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahamutech.kcore.user.adapter.CatAdapter
import com.fahamutech.kcore.user.adapter.TestimonyAdapter
import com.fahamutech.kcore.user.database.connector.HomeDataSource
import com.fahamutech.kcore.user.model.Category
import com.fahamutech.kcore.user.model.Testimony
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

class HomeNoSqlDatabase(context: Context?) : NoSqlDatabase(
    context!!
), HomeDataSource {
    private val categories: MutableList<Category> = ArrayList()
    private val testimonies: MutableList<Testimony> = ArrayList()
    override fun getCategory(recyclerView: RecyclerView?, swipeRefreshLayout: SwipeRefreshLayout?) {
        swipeRefreshLayout!!.isRefreshing = true
        val querySnapshotTask = firestore.collection(NoSqlColl.CATEGORY.name).get()
        querySnapshotTask
            .addOnSuccessListener { queryDocumentSnapshots: QuerySnapshot ->
                categories.addAll(
                    queryDocumentSnapshots.toObjects(
                        Category::class.java
                    )
                )
                val catAdapter = CatAdapter(categories, context)
                recyclerView!!.layoutManager = GridLayoutManager(context, 1)
                recyclerView.adapter = catAdapter
                swipeRefreshLayout.isRefreshing = false
                Log.e("TAG***", " done get categories")
            }.addOnFailureListener { e: Exception ->
                Log.e("TAG***", "Failed to get category : $e")
                swipeRefreshLayout.isRefreshing = false
            }
    }

    override fun getTestimony(
        recyclerView: RecyclerView?,
        swipeRefreshLayout: SwipeRefreshLayout?
    ) {
        swipeRefreshLayout!!.isRefreshing = true
        val collection = firestore.collection(NoSqlColl.TESTIMONY.name)
        collection
            .get()
            .addOnSuccessListener { queryDocumentSnapshots: QuerySnapshot ->
                testimonies.addAll(
                    queryDocumentSnapshots.toObjects(
                        Testimony::class.java
                    )
                )
                val testimonyAdapter = TestimonyAdapter(context, testimonies)
                recyclerView!!.layoutManager = GridLayoutManager(context, 2)
                recyclerView.adapter = testimonyAdapter
                swipeRefreshLayout.isRefreshing = false
            }
            .addOnFailureListener { e: Exception ->
                Log.e("TAG***", "Failed to get testimonies : $e")
                swipeRefreshLayout.isRefreshing = false
            }
    }
}