package com.fahamutech.adminapp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;

import com.fahamutech.adminapp.R;

public class NewArticleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText title;
    private TextInputEditText message;
    private ImageView imageView;
    private Button upload;

    private String imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimony);
        bindView();
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            title.setText(R.string.new_article);
        }

    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.testimony_title);
        message = findViewById(R.id.testimony_content);
        imageView = findViewById(R.id.testimony_image);
        upload = findViewById(R.id.testimony_upload);


        upload.setOnClickListener(v -> {
            if (title.getText().toString().isEmpty()) {
                title.requestFocus();
                Snackbar.make(v, "Fill the title", Snackbar.LENGTH_SHORT).show();
            } else if (message.getText().toString().isEmpty()) {
                message.requestFocus();
                Snackbar.make(v, "Fill the message", Snackbar.LENGTH_SHORT).show();
            } else if (imageUrl != null) {
                imageView.requestFocus();
                Snackbar.make(v, "Upload image please", Snackbar.LENGTH_SHORT).show();
            } else {
                uploadArticle();
            }

        });
    }

    private void uploadArticle() {
    }

}
