package com.fahamutech.adminapp.database.connector;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

public interface HomeDataSource {

    Object getCategory(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);

    Object getTestimony(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);

}
