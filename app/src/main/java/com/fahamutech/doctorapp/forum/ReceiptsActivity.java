package com.fahamutech.doctorapp.forum;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fahamutech.doctorapp.R;
import com.fahamutech.doctorapp.forum.adapter.ReceiptsAdapter;
import com.fahamutech.doctorapp.forum.database.DataBaseCallback;
import com.fahamutech.doctorapp.forum.database.UserDataSource;
import com.fahamutech.doctorapp.forum.database.UserNoSqlDataBase;
import com.fahamutech.doctorapp.forum.model.Receipt;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ReceiptsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_activity_receipts);
        bindView();

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Payment History");
        }

        //debug
        fabAction();
        //initialize ui with fake data
        initUI();
        //testing
        swipeRefreshLayout.setOnRefreshListener(this::initUI);
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.receipt_swipe);
        recyclerView = findViewById(R.id.receipt_recy);
        fab = findViewById(R.id.fab);
    }

    private void fabAction() {
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }

    private void initUI() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userDataSource = new UserNoSqlDataBase(this);
            userDataSource.getReceipts(currentUser.getEmail(), swipeRefreshLayout, (DataBaseCallback) data -> {
                List<Receipt> receipts = (List<Receipt>) data;
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new ReceiptsAdapter(this, receipts));
            });
        }
    }

}
