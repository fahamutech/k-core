package com.fahamutech.doctorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.fahamutech.doctorapp.R;
import com.fahamutech.doctorapp.adapter.HomePageFragmentAdapter;
import com.fahamutech.doctorapp.forum.ForumMainActivity;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BillingProcessor billingProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        bindView();
        setSupportActionBar(toolbar);

        //billing
        billingProcessor = BillingProcessor
                .newBillingProcessor(this, getString(R.string.play_licence), this);
        billingProcessor.initialize();

        //tab layout
        initViewPager();

        //pay
        initVipContent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initVipContent() {
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Chat is opening...", Snackbar.LENGTH_SHORT).show();
            //startActivity(new Intent(this, ForumMainActivity.class));
            if (billingProcessor.isOneTimePurchaseSupported()) {
                TransactionDetails purchaseTransactionDetails =
                        billingProcessor.getPurchaseTransactionDetails(getString(R.string.chat_product));
                if (purchaseTransactionDetails != null) {
                    //Log.e("TAG**PURCHASE","purchase not equal to null");
                    billingProcessor.purchase(this, getString(R.string.chat_product));
                } else {
                    Log.e("TAG**PURCHASE null", "purchase is equal null");
                    billingProcessor.purchase(this, getString(R.string.chat_product));
                }
            }
            //billingProcessor.purchase(this, "android.test.purchased");
        });
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.home_chat_fab);
        viewPager = findViewById(R.id.home_viewpager);
        tabLayout = findViewById(R.id.home_tab_layout);
    }

    private void initViewPager() {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.setAdapter(new HomePageFragmentAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Log.e("PAY PURCHASED", productId);
        //todo : check the date of the product purchase in order to consume it
        startActivity(new Intent(this, ForumMainActivity.class));
        // billingProcessor.consumePurchase(productId);
    }

    @Override
    public void onPurchaseHistoryRestored() {
        Log.e("PAY RESTORE", "purchase restored");
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Log.e("PAY ERROR", "error when try to bill :-> code " + String.valueOf(errorCode));
    }

    @Override
    public void onBillingInitialized() {
        Log.e("TAG BILL", "payment is initialized");
    }

    @Override
    protected void onDestroy() {
        if (billingProcessor != null) {
            billingProcessor.release();
        }
        super.onDestroy();
    }
}
