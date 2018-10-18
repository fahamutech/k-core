package com.fahamutech.adminapp.forum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fahamutech.adminapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class PayActivity extends AppCompatActivity {
    /**
     * parameters
     * 1.email
     * 2.number
     * 3.dsc
     * 4.amount
     * 5.callback
     */
    private static String PAY = "https://us-central1-money-fast-firebase.cloudfunctions.net/send_money?";

    //private SwipeRefreshLayout swipeRefreshLayout;
    //WebView webView;
    private Toolbar toolbar;
    //private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_activity_pay);
        bindViews();

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Pay");
        }

        //auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        } else {
            //swipe layout
            initUI();
//            fab.setOnClickListener(view ->
//                    Snackbar.make(view,
//                            "Selected amount : " + getIntent().getStringExtra("_amount"),
//                            Snackbar.LENGTH_SHORT)
//                            .setAction("Action", null).show());
        }
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initUI() {
//        String params = "email=" + firebaseAuth.getCurrentUser().getEmail() +
//                "&dsc=PaymentModel For Kemifra App&amount=1000&callback=doctor-fahamutech.firebaseapp.com";
//        webView.loadUrl(PAY + params);
//        webView.setWebViewClient(new MyWebClient());
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            webView.loadUrl(PAY + params);
//        });

    }

//    private class MyWebClient extends WebViewClient {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//           // swipeRefreshLayout.setRefreshing(true);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//           // swipeRefreshLayout.setRefreshing(false);
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode,
//                                    String description, String failingUrl) {
//            Snackbar.make(view, "Error loading page", Snackbar.LENGTH_LONG).show();
//        }
//    }
}
