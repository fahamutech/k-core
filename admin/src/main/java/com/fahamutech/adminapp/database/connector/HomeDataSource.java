package com.fahamutech.adminapp.database.connector;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.fahamutech.adminapp.database.DataBaseCallback;

public interface HomeDataSource {

    Object getCategory(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);

    Object getTestimony(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);

}
