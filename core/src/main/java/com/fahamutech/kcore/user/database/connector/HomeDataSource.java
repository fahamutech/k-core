package com.fahamutech.kcore.user.database.connector;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public interface HomeDataSource {

    void getCategory(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);

    void getTestimony(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout);
}
