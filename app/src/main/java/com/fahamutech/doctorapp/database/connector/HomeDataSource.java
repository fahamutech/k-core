package com.fahamutech.doctorapp.database.connector;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

public interface HomeDataSource {

    void getCategory(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);

    void getTestimony(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);
}
