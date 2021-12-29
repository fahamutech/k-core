package com.fahamutech.kcore.user.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.fahamutech.kcore.R
import com.fahamutech.kcore.user.adapter.HomePageFragmentAdapter
import com.fahamutech.kcore.utils.getKCoreFirebaseApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.google.android.material.tabs.TabLayout.ViewPagerOnTabSelectedListener
import com.google.firebase.FirebaseApp

class JifunzeActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var fab: FloatingActionButton? = null
    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKCoreFirebaseApp(this)
        setContentView(R.layout.app_bar_main_user)
        bindView()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Jifunze"
        //tab layout
        initViewPager()
        //pay
        initVipContent()
    }

    private fun initVipContent() {
        fab!!.setOnClickListener { view: View? -> onBackPressed() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        Log.e("TAGGG","Go back home")
        return true
    }

    private fun bindView() {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Jifunze"
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
            Log.e("TAGGG","Go back home")
        }
        fab = findViewById(R.id.home_chat_fab)
        viewPager = findViewById(R.id.home_viewpager)
        tabLayout = findViewById(R.id.home_tab_layout)
    }

    private fun initViewPager() {
        tabLayout!!.setupWithViewPager(viewPager)
        viewPager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(ViewPagerOnTabSelectedListener(viewPager))
        viewPager!!.adapter =
            HomePageFragmentAdapter(supportFragmentManager)
    }
}