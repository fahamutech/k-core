package com.fahamutech.kcore.admin.database.connector

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView

interface ArticleDataSource {
    fun getAllById(
        categoryId: String?, recyclerView: RecyclerView?,
        swipeRefreshLayout: SwipeRefreshLayout?
    )
}