package com.fahamutech.adminapp.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.fahamutech.adminapp.R;
import com.fahamutech.adminapp.forum.database.PostNoSqlDataBase;
import com.fahamutech.adminapp.forum.fragment.AllChartFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForumMainActivity extends AppCompatActivity {

    private PostNoSqlDataBase postNoSqlDataBase;

    @Override
    protected void onStart() {
        checkIsLogin();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_activity_main);
        Toolbar toolbar = findViewById(R.id.forum_toolbar2);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Kemifra Chat");
        }

        //render the view
        iniUI();
        //check if user is exist
        checkIsLogin();
    }

    @Override
    protected void onResume() {
        checkIsLogin();
        super.onResume();
    }

    private void checkIsLogin() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignUpActivity.class));
        } else {
            //set online
            postNoSqlDataBase = new PostNoSqlDataBase(this);
            postNoSqlDataBase.online(currentUser.getUid());
        }
    }

    /**
     * render the chat view
     */
    private void iniUI() {
        ViewPager mViewPager = findViewById(R.id.container_view);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("ALL CHATS");

    }

    /**
     * set up the viewer page to use with tab layout
     *
     * @param mViewPager the viewer pager
     */
    private void setupViewPager(ViewPager mViewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        //doctor fragment
        adapter.addFragment(new AllChartFragment());
        mViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.forum_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            try {
                postNoSqlDataBase.offline(currentUser.getUid());
            } catch (NullPointerException e) {
                new PostNoSqlDataBase(this).offline(currentUser.getUid());
            }
        }
        super.onDestroy();
    }
}
