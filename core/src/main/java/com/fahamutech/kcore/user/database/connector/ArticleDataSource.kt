package com.fahamutech.kcore.user.database.connector

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView

interface ArticleDataSource {
    fun getArticles(
        categoryId: String?, recyclerView: RecyclerView?,
        swipeRefreshLayout: SwipeRefreshLayout?
    )
}