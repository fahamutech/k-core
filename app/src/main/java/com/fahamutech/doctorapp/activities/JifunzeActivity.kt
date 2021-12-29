package com.fahamutech.doctorapp.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.os.Bundle
import android.util.Log
import com.fahamutech.doctorapp.R
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.google.android.material.tabs.TabLayout.ViewPagerOnTabSelectedListener
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.fahamutech.doctorapp.adapter.HomePageFragmentAdapter
import com.google.firebase.FirebaseApp

class JifunzeActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var fab: FloatingActionButton? = null
    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.app_bar_main)
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