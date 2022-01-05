package com.fahamutech.kcore.admin.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fahamutech.kcore.R
import com.fahamutech.kcore.admin.database.connector.ArticleDataSource
import com.fahamutech.kcore.admin.database.noSql.ArticlesNoSqlDatabase
import com.fahamutech.kcore.admin.model.Category
import com.fahamutech.kcore.admin.session.Session

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
        setContentView(R.layout.activity_category_content)
        bindView()
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        val category = intent.getSerializableExtra("_category") as Category?
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            if (category != null) {
                title = category.name
                categoryId = category.id
                supportActionBar.title = "$title-Contents"
            } else if (session!!.lastCategory?.isNotEmpty() == true) {
                categoryId = session!!.lastCategory
                title = session!!.lastTitle
                supportActionBar.title = "$title-Contents"
            }
        }

        //get articles
        initContent(ArticlesNoSqlDatabase(this))
        //testing
        fab!!.setOnClickListener { }
    }

    private fun initContent(articleDataSource: ArticleDataSource) {
        articleDataSource.getAllById(categoryId, recyclerView, swipeRefreshLayout)
        swipeRefreshLayout!!.setOnRefreshListener {
            articleDataSource.getAllById(
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

        //start session
        session = Session(this)
    }
}