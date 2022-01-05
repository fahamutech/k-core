package com.fahamutech.kcore.admin.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageView
import com.fahamutech.kcore.R
import com.fahamutech.kcore.admin.database.connector.ArticleDataSource
import com.fahamutech.kcore.admin.database.noSql.ArticlesNoSqlDatabase
import com.fahamutech.kcore.admin.database.noSql.HomeNoSqlDatabase
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class NewArticleActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var title: TextInputEditText? = null
    private var message: TextInputEditText? = null
    private var imageView: ImageView? = null
    private var upload: Button? = null
    private var uploadImage: Button? = null
    private var dataSource: ArticleDataSource? = null
    private var spinner: Spinner? = null
    private var imageUrl: CropImageView.CropResult? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testimony)
        bindView()
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setTitle(R.string.new_article)
        }
        dataSource = ArticlesNoSqlDatabase(this)
        HomeNoSqlDatabase(this).getAllCategory(spinner)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//            val img = ImagePicker.getFirstImageOrNull(data)
//            val bitmap = BitmapFactory.decodeFile(img.path)
//            Glide.with(this).load(bitmap).into(imageView!!)
//            imageUrl = img
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun bindView() {
        toolbar = findViewById(R.id.toolbar)
        title = findViewById(R.id.testimony_title)
        message = findViewById(R.id.testimony_content)
        imageView = findViewById(R.id.testimony_image)
        uploadImage = findViewById(R.id.testimony_u)
        upload = findViewById(R.id.testimony_upload)
        spinner = findViewById(R.id.testimony_spinner)
        upload?.setOnClickListener { v: View? ->
            when {
                title?.text.toString().isEmpty() -> {
                    title?.requestFocus()
                    Snackbar.make(v!!, "Fill the title", Snackbar.LENGTH_SHORT).show()
                }
                message?.text.toString().isEmpty() -> {
                    message?.requestFocus()
                    Snackbar.make(v!!, "Fill the message", Snackbar.LENGTH_SHORT).show()
                }
                imageUrl == null -> {
                    uploadImage?.requestFocus()
                    Snackbar.make(v!!, "Upload image please", Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    uploadArticle()
                }
            }
        }
        uploadImage?.setOnClickListener {
//            ImagePicker.create(this)
//                .single().theme(R.style.AppTheme)
//                .returnMode(ReturnMode.ALL)
//                .showCamera(true)
//                .limit(1)
//                .start()
        }
    }

    private fun uploadArticle() {}
}