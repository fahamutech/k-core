package com.fahamutech.adminapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.fahamutech.adminapp.R;
import com.fahamutech.adminapp.database.connector.ArticleDataSource;
import com.fahamutech.adminapp.database.noSql.ArticlesNoSqlDatabase;
import com.fahamutech.adminapp.database.noSql.HomeNoSqlDatabase;
import com.fahamutech.adminapp.model.Category;
import com.fahamutech.adminapp.session.Session;
import com.google.firebase.firestore.QuerySnapshot;

public class NewArticleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText title;
    private TextInputEditText message;
    private ImageView imageView;
    private Button upload;
    private Button uploadImage;
    private ArticleDataSource dataSource;
    private Spinner spinner;

    private Image imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimony);
        bindView();
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.new_article);
        }

        dataSource = new ArticlesNoSqlDatabase(this);
        new HomeNoSqlDatabase(this).getAllCategory(spinner);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image img = ImagePicker.getFirstImageOrNull(data);
            Bitmap bitmap = BitmapFactory.decodeFile(img.getPath());
            Glide.with(this).load(bitmap).into(imageView);
            imageUrl = img;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.testimony_title);
        message = findViewById(R.id.testimony_content);
        imageView = findViewById(R.id.testimony_image);
        uploadImage = findViewById(R.id.testimony_u);
        upload = findViewById(R.id.testimony_upload);
        spinner = findViewById(R.id.testimony_spinner);


        upload.setOnClickListener(v -> {
            if (title.getText().toString().isEmpty()) {
                title.requestFocus();
                Snackbar.make(v, "Fill the title", Snackbar.LENGTH_SHORT).show();
            } else if (message.getText().toString().isEmpty()) {
                message.requestFocus();
                Snackbar.make(v, "Fill the message", Snackbar.LENGTH_SHORT).show();
            } else if (imageUrl == null) {
                uploadImage.requestFocus();
                Snackbar.make(v, "Upload image please", Snackbar.LENGTH_SHORT).show();
            } else {
                uploadArticle();
            }

        });

        uploadImage.setOnClickListener(v -> ImagePicker.create(this)
                .single().theme(R.style.AppTheme)
                .returnMode(ReturnMode.ALL)
                .showCamera(true)
                .limit(1)
                .start());
    }

    private void uploadArticle() {

    }

}
