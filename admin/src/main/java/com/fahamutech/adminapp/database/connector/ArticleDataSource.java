package com.fahamutech.adminapp.database.connector;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

public interface ArticleDataSource {
    void getArticles(String categoryId, RecyclerView recyclerView,
                     SwipeRefreshLayout swipeRefreshLayout);
}
