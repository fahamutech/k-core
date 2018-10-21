package com.fahamutech.adminapp.database.noSql;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fahamutech.adminapp.adapter.CatAdapter;
import com.fahamutech.adminapp.adapter.TestimonyAdapter;
import com.fahamutech.adminapp.database.DataBaseCallback;
import com.fahamutech.adminapp.database.connector.HomeDataSource;
import com.fahamutech.adminapp.model.Category;
import com.fahamutech.adminapp.model.Testimony;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class HomeNoSqlDatabase extends NoSqlDatabase implements HomeDataSource {

    public HomeNoSqlDatabase(Context context) {
        super(context);
    }

    @Override
    public Object getCategory(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(true);
        return firestore.collection(NoSqlColl.CATEGORY.name())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots != null) {
                        List<Category> categories = queryDocumentSnapshots.toObjects(Category.class);
                        CatAdapter catAdapter = new CatAdapter(categories, context);
                        recyclerView.setLayoutManager(new GridLayoutManager(this.context, 2));
                        recyclerView.setAdapter(catAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                        Log.e("TAG***", " done get categories");
                    }
                    if (e != null) {
                        Log.e("NO SQL ", "fail to listen to the database");
                    }
                });
    }


    public Object getTestimony(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {

        swipeRefreshLayout.setRefreshing(true);
        return firestore.collection(NoSqlColl.TESTIMONY.name())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots != null) {
                        List<Testimony> testimonies = queryDocumentSnapshots.toObjects(Testimony.class);
                        TestimonyAdapter testimonyAdapter = new TestimonyAdapter(this.context, testimonies);
                        recyclerView.setLayoutManager(new GridLayoutManager(this.context, 2));
                        recyclerView.setAdapter(testimonyAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (e != null) {
                        Log.e("get testimony", "failed " + e.getLocalizedMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

}
