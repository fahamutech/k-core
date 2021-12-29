package com.fahamutech.adminapp.database.connector;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fahamutech.adminapp.forum.database.DataBaseCallback;
import com.fahamutech.adminapp.model.Article;

public interface ArticleDataSource {
    void getAllById(String categoryId, RecyclerView recyclerView,
                    SwipeRefreshLayout swipeRefreshLayout);

    void getAll(DataBaseCallback... callbacks);

    void createArticle(Article article, DataBaseCallback... callbacks);

    void deleteArticle(String docId, DataBaseCallback... callbacks);
}
