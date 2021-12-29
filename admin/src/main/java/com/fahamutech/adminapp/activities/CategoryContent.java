package com.fahamutech.adminapp.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.fahamutech.adminapp.R;
import com.fahamutech.adminapp.forum.ForumMainActivity;
import com.fahamutech.adminapp.database.connector.ArticleDataSource;
import com.fahamutech.adminapp.database.noSql.ArticlesNoSqlDatabase;
import com.fahamutech.adminapp.model.Category;
import com.fahamutech.adminapp.session.Session;

public class CategoryContent extends AppCompatActivity {

    private Session session;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private String title;
    private String categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_content);
        bindView();
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        Category category = (Category) getIntent().getSerializableExtra("_category");

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            if (category != null) {
                title = category.getName();
                categoryId = category.getId();
                supportActionBar.setTitle(title + "-" + "Contents");
            } else if (!session.getLastCategory().isEmpty()) {
                categoryId = session.getLastCategory();
                title = session.getLastTitle();
                supportActionBar.setTitle(title + "-" + "Contents");
            }
        }

        //get articles
        initContent(new ArticlesNoSqlDatabase(this));
        //testing
        fab.setOnClickListener(view -> {
            Snackbar.make(view,
                    "Chat opening...", Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(this, ForumMainActivity.class));
        });
    }

    private void initContent(ArticleDataSource articleDataSource) {
        articleDataSource.getAllById(categoryId, recyclerView, swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() ->
                articleDataSource.getAllById(categoryId, recyclerView, swipeRefreshLayout));

    }

    private void bindView() {
        recyclerView = findViewById(R.id.article_recy);
        swipeRefreshLayout = findViewById(R.id.article_swipe);
        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);

        //start session
        session = new Session(this);
    }

}
