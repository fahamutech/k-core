package com.fahamutech.kcore.admin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fahamutech.kcore.R
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.fahamutech.kcore.admin.model.Testimony
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class SimpleImageViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_image_viewer)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.title = "Image Viewer"
            supportActionBar.subtitle = "Pinch To Zoom"
        }
        val intent = intent
        val imageView = findViewById<ImageView>(R.id.image_image)
        if (intent != null) {
            val testimony = intent.getSerializableExtra("_image_") as Testimony?
            Glide.with(this).load(testimony!!.image).into(imageView)

//            imageView.setOnTouchListener(ImageMatrixTouchHandler(imageView.context))
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view: View? ->
            Snackbar.make(
                view!!, "Share project", Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
        }
    }
}