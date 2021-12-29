package com.fahamutech.kcore.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fahamutech.kcore.R;
import com.fahamutech.kcore.admin.adapter.HomePageFragmentAdapter;

public class JifunzeAdminActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        bindView();
        setSupportActionBar(toolbar);

        //tab layout
        initViewPager();

        //for testing
        fab.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_category) {
            startActivity(new Intent(this, CategoryActivity.class));
            return true;
        }else if (id==R.id.action_new_article){
            startActivity(new Intent(this,NewArticleActivity.class));
        }

        return super.onOptionsItemSelected(item);
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
}
