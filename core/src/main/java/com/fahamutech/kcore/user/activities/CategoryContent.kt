package com.fahamutech.kcore.user.activities

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.fahamutech.kcore.R
import com.fahamutech.kcore.user.database.connector.ArticleDataSource
import com.fahamutech.kcore.user.database.noSql.ArticlesNoSqlDatabase
import com.fahamutech.kcore.user.model.Category
import com.fahamutech.kcore.user.session.Session
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        val category = intent.getSerializableExtra("_category") as Category?
        if (category != null) {
            title = category.name
            categoryId = category.id
        } else if (session!!.lastCategory?.isNotEmpty() == true) {
            categoryId = session!!.lastCategory
            title = session!!.lastTitle
        }
        bindView()
        initContent(ArticlesNoSqlDatabase(this))
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
        toolbar?.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        session = Session(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}