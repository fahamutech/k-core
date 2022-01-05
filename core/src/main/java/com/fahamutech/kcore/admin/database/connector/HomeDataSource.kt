package com.fahamutech.kcore.admin.database.connector

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView

interface HomeDataSource {
    fun getCategory(recyclerView: RecyclerView?, swipeRefreshLayout: SwipeRefreshLayout?): Any?
    fun getTestimony(recyclerView: RecyclerView?, swipeRefreshLayout: SwipeRefreshLayout?): Any?
}