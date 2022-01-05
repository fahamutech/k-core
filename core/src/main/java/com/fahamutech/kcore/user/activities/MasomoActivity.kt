package com.fahamutech.kcore.user.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.fahamutech.kcore.R
import com.fahamutech.kcore.utils.getKCoreFirebaseApp
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MasomoActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var fab: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKCoreFirebaseApp(this)
        setContentView(R.layout.masomo_activity_user)
        bindView(this)
        initViewPager()
        initVipContent()
    }

    private fun initVipContent() {
        fab!!.setOnClickListener { onBackPressed() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        Log.e("TAGGG","Go back home")
        return true
    }

    private fun bindView(context: Context) {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Masomo"
        toolbar?.navigationIcon = AppCompatResources.getDrawable(context,R.drawable.ic_baseline_arrow_back_24)
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
            Log.e("TAGGG","Go back home")
        }
        fab = findViewById(R.id.home_chat_fab)
    }

    private fun initViewPager() {
    }
}