package com.fahamutech.doctorapp.forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fahamutech.doctorapp.R;
import com.fahamutech.doctorapp.forum.database.ForumC;
import com.fahamutech.doctorapp.forum.database.PostDataSource;
import com.fahamutech.doctorapp.forum.database.PostNoSqlDataBase;
import com.fahamutech.doctorapp.forum.fragment.AllChartFragment;
import com.fahamutech.doctorapp.forum.fragment.MyChatFragment;
import com.fahamutech.doctorapp.forum.model.ChatTopic;
import com.fahamutech.doctorapp.forum.model.UserSubscription;
import com.fahamutech.doctorapp.session.Session;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class ForumMainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PostDataSource noSqlDatabase;

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

        //initiate database
        noSqlDatabase = new PostNoSqlDataBase(this);

        //render the view
        iniUI();
        //check if user is exist
        checkIsLogin();

        //check pay
        checkThePay();
    }

    @Override
    protected void onResume() {
        checkIsLogin();
        super.onResume();
    }

    /**
     * check if user is exist in firebase
     */
    private void checkIsLogin() {
        Session session = new Session(this);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignUpActivity.class));
            //finish();
        } else {
            //check if user is paying
            String userPay = session.getUserPay();
            if (userPay.equals(Session.PAY_D)) {
                Toast.makeText(this, "Please pay first", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (userPay.equals(Session.PAY_NOT_OK)) {
                Toast.makeText(this, "Please pay first", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ProfileActivity.class));
            }
        }
    }

    /**
     * render the chat view
     */
    private void iniUI() {
        mViewPager = findViewById(R.id.container_view);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        FloatingActionButton fab = findViewById(R.id.forum_new);

        tabLayout.getTabAt(0).setText("MY CHATS");
        //tabLayout.getTabAt(1).setText("ALL CHATS");

        //fab listener
        tabAction(tabLayout, fab);
        createForum(fab, this);

    }

    /**
     * hide the float button if not in the tab of my forum
     *
     * @param tabLayout -
     * @param fab       -
     */
    private void tabAction(final TabLayout tabLayout, final FloatingActionButton fab) {
        // if (mViewPager != null) mViewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * create the new topic
     *
     * @param fab     the to initiate the popup
     * @param context the activity
     */
    private void createForum(FloatingActionButton fab, Context context) {

        fab.setOnClickListener(v -> {
            @SuppressLint("InflateParams") LinearLayout view = (LinearLayout) LayoutInflater
                    .from(this).inflate(R.layout.forum_create_forum, null);

            new MaterialStyledDialog.Builder(context)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.ic_mode_edit_black_48dp)
                    .setCustomView(view, 20, 20, 20, 20)
                    .setScrollable(true)
                    .setCancelable(false)
                    .setPositiveText("Ok")
                    .setNegativeText("Cancel")
                    .onPositive((dialog, which) -> {
                        if (dialog.getCustomView() != null) {
                            EditText editText = dialog.getCustomView()
                                    .findViewById(R.id.new_forum_title);
                            EditText maelezoEd = dialog.getCustomView()
                                    .findViewById(R.id.new_forum_title_forum_description);
                            if (!editText.getText().toString().equals("")
                                    && !maelezoEd.getText().toString().equals("")) {

                                String Uid = FirebaseAuth.getInstance().getUid();
                                String image;
                                try {
                                    image = FirebaseAuth.getInstance().getCurrentUser()
                                            .getPhotoUrl().toString();
                                } catch (NullPointerException e) {
                                    image = "";
                                }

                                String time = String.valueOf(new Date().getTime());
                                //add the new topic to the database
                                //OnlineStatus onlineStatus = new OnlineStatus(ChatEnum.online.name(), "");
                                noSqlDatabase.createChatTopic(
                                        new ChatTopic(
                                                editText.getText().toString().trim(),
                                                maelezoEd.getText().toString().trim(),
                                                Uid,
                                                time,
                                                image,
                                                true,
                                                false
                                        )
                                );
                                dialog.dismiss();
                            } else {
                                Snackbar.make(dialog.getView(), "Fill all the details",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                            Snackbar.make(mViewPager, "Error, try again",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .onNegative((dialog, which) -> {
                        dialog.dismiss();
                        Snackbar.make(mViewPager, "Canceled...", Snackbar.LENGTH_SHORT).show();
                    })
                    .show();
        });
    }

    /**
     * set up the viewer page to use with tab layout
     *
     * @param mViewPager the viewer pager
     */
    private void setupViewPager(ViewPager mViewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        //patient fragment
        adapter.addFragment(new MyChatFragment());
        //doctor fragment
        //adapter.addFragment(new AllChartFragment());
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

    private void checkThePay() {
        Session session = new Session(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String docId = user.getEmail();
            FirebaseFirestore.getInstance().collection(ForumC.FORUM_USER.name())
                    .document(docId)
                    .collection(ForumC.SUBSCRIPTION.name())
                    .orderBy("end")
                    .limit(1)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<UserSubscription> userSubscriptions
                                = queryDocumentSnapshots.toObjects(UserSubscription.class);
                        if (userSubscriptions.size() > 0) {
                            UserSubscription subscription = userSubscriptions.get(0);
                            if (subscription != null) {
                                long l = Long.parseLong(subscription.getEnd());
                                if (l > System.currentTimeMillis())
                                    session.userPay(Session.PAY_OK);
                                else session.userPay(Session.PAY_NOT_OK);
                            } else {
                                session.userPay(Session.PAY_NOT_OK);
                            }
                        } else {
                            session.userPay(Session.PAY_NOT_OK);
                        }
                    })
                    .addOnFailureListener(e -> {
                        session.userPay(Session.PAY_NOT_OK);
                    });
        }

    }
}
