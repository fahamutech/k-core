package com.fahamutech.kcore.admin.database.noSql;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fahamutech.kcore.admin.adapter.CatAdapter;
import com.fahamutech.kcore.admin.adapter.TestimonyAdapter;
import com.fahamutech.kcore.admin.database.connector.HomeDataSource;
import com.fahamutech.kcore.admin.model.Category;
import com.fahamutech.kcore.admin.model.Testimony;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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
                        //save this list of category for later use
                        // MainActivity.categoryList = categories;
                        //new Session(context).saveCategories(categories);
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

    public void getAllCategory(Spinner spinner) {
        firestore.collection(NoSqlColl.CATEGORY.name())
                .addSnapshotListener((QuerySnapshot snapshots, FirebaseFirestoreException e) -> {
                    if (snapshots != null) {
                        List<String> strings = new ArrayList<>();
                        List<Category> cat = snapshots.toObjects(Category.class);
                        for (Category a : cat) {
                            strings.add(a.getName());
                        }
                        ArrayAdapter<String> adapter
                                = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
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
