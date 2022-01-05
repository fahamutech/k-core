package com.fahamutech.kcore.admin.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.os.Bundle
import com.fahamutech.kcore.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.fahamutech.kcore.admin.activities.CategoryActivity
import com.fahamutech.kcore.admin.activities.NewArticleActivity
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.fahamutech.kcore.admin.adapter.HomePageFragmentAdapter

class JifunzeAdminActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var fab: FloatingActionButton? = null
    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_main)
        bindView()
        setSupportActionBar(toolbar)

        //tab layout
        initViewPager()

        //for testing
        fab!!.setOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_new_category) {
            startActivity(Intent(this, CategoryActivity::class.java))
            return true
        } else if (id == R.id.action_new_article) {
            startActivity(Intent(this, NewArticleActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindView() {
        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.home_chat_fab)
        viewPager = findViewById(R.id.home_viewpager)
        tabLayout = findViewById(R.id.home_tab_layout)
    }

    private fun initViewPager() {
        tabLayout!!.setupWithViewPager(viewPager)
        viewPager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        viewPager!!.adapter = HomePageFragmentAdapter(supportFragmentManager)
    }
}