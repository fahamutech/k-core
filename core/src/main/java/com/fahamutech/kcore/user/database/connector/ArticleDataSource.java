package com.fahamutech.kcore.user.database.connector;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public interface ArticleDataSource {
    void getArticles(String categoryId, RecyclerView recyclerView,
                     SwipeRefreshLayout swipeRefreshLayout);
}
