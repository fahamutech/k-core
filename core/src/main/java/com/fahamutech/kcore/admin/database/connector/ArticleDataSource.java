package com.fahamutech.kcore.admin.database.connector;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;


public interface ArticleDataSource {
    void getAllById(String categoryId, RecyclerView recyclerView,
                    SwipeRefreshLayout swipeRefreshLayout);

}
