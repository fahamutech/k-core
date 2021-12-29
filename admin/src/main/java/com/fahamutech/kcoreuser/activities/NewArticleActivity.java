package com.fahamutech.kcoreuser.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.fahamutech.kcoreuser.R;
import com.fahamutech.kcoreuser.database.connector.ArticleDataSource;
import com.fahamutech.kcoreuser.database.noSql.ArticlesNoSqlDatabase;
import com.fahamutech.kcoreuser.database.noSql.HomeNoSqlDatabase;

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
