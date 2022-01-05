package com.fahamutech.kcore.admin.database.noSql;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fahamutech.kcore.admin.adapter.ArtAdapter;
import com.fahamutech.kcore.admin.database.connector.ArticleDataSource;
import com.fahamutech.kcore.admin.model.Article;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ArticlesNoSqlDatabase extends NoSqlDatabase implements ArticleDataSource {

    public ArticlesNoSqlDatabase(Context context) {
        super(context);
    }

    @Override
    public void getAllById(String categoryId, RecyclerView recyclerView,
                           SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(true);
        firestore.collection(NoSqlColl.ARTICLES.name())
                .whereEqualTo("categoryId", categoryId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Article> articles = queryDocumentSnapshots.toObjects(Article.class);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new ArtAdapter(articles, context));
                    swipeRefreshLayout.setRefreshing(false);
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(swipeRefreshLayout, "Failed to get articles", Snackbar.LENGTH_SHORT).show();
                    Log.e("TAG ARTICLES", String.valueOf(e));
                    swipeRefreshLayout.setRefreshing(false);
                });
    }




}
