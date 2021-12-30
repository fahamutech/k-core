package com.fahamutech.kcore.user.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatDrawableManager
import androidx.appcompat.widget.Toolbar
import com.fahamutech.kcore.R
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.fahamutech.kcore.user.database.connector.ArticleDataSource
import com.fahamutech.kcore.user.database.noSql.ArticlesNoSqlDatabase
import com.fahamutech.kcore.user.model.Category
import com.fahamutech.kcore.user.session.Session

class CategoryContent : AppCompatActivity() {
    private var session: Session? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var fab: FloatingActionButton? = null
    private var toolbar: Toolbar? = null
    private var title: String? = null
    private var categoryId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_content_user)
        //        setSupportActionBar(toolbar);
//        ActionBar supportActionBar = getSupportActionBar();
        val category = intent.getSerializableExtra("_category") as Category?

//        if (supportActionBar != null) {
//            supportActionBar.setDisplayHomeAsUpEnabled(true);
        if (category != null) {
            title = category.name
            categoryId = category.id
            //                supportActionBar.setTitle(title);
        } else if (!session!!.lastCategory.isEmpty()) {
            categoryId = session!!.lastCategory
            title = session!!.lastTitle
            //                supportActionBar.setTitle(title);
        }
        bindView()
        //        }

        //get articles
        initContent(ArticlesNoSqlDatabase(this))
        //testing
//        fab.setOnClickListener(view -> {
//            Snackbar.make(view,
//                    "Chat opening...", Snackbar.LENGTH_LONG).show();
////            startActivity(new Intent(this, ForumMainActivity.class));
//        });
    }

    private fun initContent(articleDataSource: ArticleDataSource) {
        articleDataSource.getArticles(categoryId, recyclerView, swipeRefreshLayout)
        swipeRefreshLayout!!.setOnRefreshListener {
            articleDataSource.getArticles(
                categoryId,
                recyclerView,
                swipeRefreshLayout
            )
        }
    }

    private fun bindView() {
        recyclerView = findViewById(R.id.article_recy)
        swipeRefreshLayout = findViewById(R.id.article_swipe)
        fab = findViewById(R.id.fab)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = title
        toolbar?.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.md_nav_back)
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        //start session
        session = Session(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}