package com.fahamutech.kcore.user.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.fahamutech.kcore.R
import com.fahamutech.kcore.utils.getKCoreFirebaseApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class UshuhudaActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var fab: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKCoreFirebaseApp(this)
        setContentView(R.layout.ushuhuda_activity_user)
        bindView(this)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.title = "Jifunze"
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

    private fun bindView(context: Context) {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Ushuhuda"
        toolbar?.navigationIcon = AppCompatResources.getDrawable(context,R.drawable.md_nav_back)
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
            Log.e("TAGGG","Go back home")
        }
        fab = findViewById(R.id.home_chat_fab)
//        viewPager = findViewById(R.id.home_viewpager)
//        tabLayout = findViewById(R.id.home_tab_layout)
    }

    private fun initViewPager() {
//        tabLayout!!.setupWithViewPager(viewPager)
//        viewPager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
//        tabLayout!!.addOnTabSelectedListener(ViewPagerOnTabSelectedListener(viewPager))
//        viewPager!!.adapter =
//            HomePageFragmentAdapter(supportFragmentManager)
    }
}