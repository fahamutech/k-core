package com.fahamutech.kcore.user.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.fahamutech.kcore.R
import com.google.android.material.snackbar.Snackbar
import com.bumptech.glide.Glide
import com.fahamutech.kcore.user.model.Article

class ReadActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
//    private var fab: FloatingActionButton? = null
    private var title: TextView? = null
    private var content: TextView? = null
    private var image: ImageView? = null

    //    private ImageView favorite;
    //    private ImageView share;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_user)
        bindView()
        findViewById<View>(R.id.toolbar)
        setSupportActionBar(toolbar)
        //        ActionBar supportActionBar = getSupportActionBar();
        val article = intent.getSerializableExtra("_article") as Article?
        //        if (supportActionBar != null) {
//            supportActionBar.setDisplayHomeAsUpEnabled(true);
//            if (article != null) supportActionBar.setTitle(article.getTitle());
//        }

        //load article
        article?.let { iniView(it) }

//        //testing
//        fab!!.setOnClickListener { view: View? ->
//            Snackbar.make(
//                view!!, "Share opening...", Snackbar.LENGTH_LONG
//            )
//                .setAction("Action", null).show()
//        }
    }

    private fun iniView(article: Article) {
        title!!.text = article.title
        content!!.text = article.content
        Glide.with(this).load(article.image).into(image!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun bindView() {
        toolbar = findViewById(R.id.toolbar)
//        fab = findViewById(R.id.fab)
        content = findViewById(R.id.read_content)
        title = findViewById(R.id.read_title)
        image = findViewById(R.id.read_image)
        toolbar?.title = "Soma"
        toolbar?.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        //        favorite = findViewById(R.id.read_like);
//        share = findViewById(R.id.read_share);
    }
}