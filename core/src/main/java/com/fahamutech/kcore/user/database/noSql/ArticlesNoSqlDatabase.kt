package com.fahamutech.kcore.user.database.noSql

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahamutech.kcore.user.adapter.ArtAdapter
import com.fahamutech.kcore.user.database.connector.ArticleDataSource
import com.fahamutech.kcore.user.model.Article
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class ArticlesNoSqlDatabase(context: Context?) : NoSqlDatabase(
    context!!
), ArticleDataSource {
    override fun getArticles(
        categoryId: String?, recyclerView: RecyclerView?,
        swipeRefreshLayout: SwipeRefreshLayout?
    ) {
        swipeRefreshLayout!!.isRefreshing = true
        firestore.collection(NoSqlColl.ARTICLES.name)
            .whereEqualTo("categoryId", categoryId)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots: QuerySnapshot ->
                val articles = queryDocumentSnapshots.toObjects(
                    Article::class.java
                )
                recyclerView!!.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = ArtAdapter(articles, context)
                swipeRefreshLayout.isRefreshing = false
            }
            .addOnFailureListener { e: Exception ->
                Snackbar.make(swipeRefreshLayout, "Failed to get articles", Snackbar.LENGTH_SHORT)
                    .show()
                Log.e("TAG ARTICLES", e.toString())
                swipeRefreshLayout.isRefreshing = false
            }
    }
}