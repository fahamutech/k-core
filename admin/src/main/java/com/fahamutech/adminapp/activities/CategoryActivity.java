package com.fahamutech.adminapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;

import com.esafirm.imagepicker.helper.ImagePickerUtils;
import com.fahamutech.adminapp.R;

public class CategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputEditText title;
    private TextInputEditText description;
    private ImageView image;
    private Button uploadButton;

    private String imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        bindView();

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("New Category");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        fab.setOnClickListener(view -> createCategory());
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.cat_new_name);
        description = findViewById(R.id.cat_new_description);
        fab = findViewById(R.id.fab);
        image = findViewById(R.id.cat_new_image);
        uploadButton = findViewById(R.id.cat_new_upload);

        uploadButton.setOnClickListener(v->{
            ImagePickerUtils imagePickerUtils = new ImagePickerUtils();
        });
    }

    private void createCategory() {
        if (title.getText().toString().isEmpty()) {
            title.requestFocus();
            snackMessage("Fill the title of the category");
        } else if (description.getText().toString().isEmpty()) {
            description.requestFocus();
            snackMessage("Fill the description of the category");
        } else if (imageUrl == null) {
            uploadButton.requestFocus();
            snackMessage("Upload image for the category");
        } else {
            snackMessage("Category creating");
        }
    }

    private void snackMessage(String message) {
        Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show();
    }

}
