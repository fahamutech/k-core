package com.fahamutech.kcore.user.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fahamutech.kcore.R
import androidx.appcompat.content.res.AppCompatResources
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.fahamutech.kcore.user.model.Testimony

class SimpleImageViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_image_viewer_user)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar?.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.md_nav_back)
        toolbar?.title = "Picha"
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        //        setSupportActionBar(toolbar);

//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null) {
//            supportActionBar.setDisplayHomeAsUpEnabled(true);
//            supportActionBar.setTitle("Image Viewer");
//            supportActionBar.setSubtitle("Pinch To Zoom");
//        }
        val intent = intent
        val imageView = findViewById<ImageView>(R.id.image_image)
        if (intent != null) {
            val testimony = intent.getSerializableExtra("_image_") as Testimony?
            Glide.with(this).load(testimony!!.image).into(imageView)

            //pinch to zoom
            imageView.setOnTouchListener(ImageMatrixTouchHandler(imageView.context))
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view ->
//                Snackbar.make(view, "Share project", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}