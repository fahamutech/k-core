package com.fahamutech.adminapp.forum;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.fahamutech.adminapp.R;
import com.fahamutech.adminapp.activities.MainActivity;
import com.fahamutech.adminapp.forum.database.DataBaseCallback;
import com.fahamutech.adminapp.forum.database.PostNoSqlDataBase;
import com.fahamutech.adminapp.forum.database.UserDataSource;
import com.fahamutech.adminapp.forum.database.UserNoSqlDataBase;
import com.fahamutech.adminapp.forum.model.Doctor;
import com.fahamutech.adminapp.session.Session;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputEditText phoneEditText;
    private TextInputEditText fullName;
    private TextInputEditText email;
    private TextInputEditText addressTextEdit;
    private Button updateProfileButton;
    private CircleImageView circleImageView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserDataSource noSqlDatabase;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_activity_sprofile);
        bindView();

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Profile");
        }

        noSqlDatabase = new UserNoSqlDataBase(this);
        //session
        session = new Session(this);
        //for testing
        contactUs();
        //listener
        buttons();
        //user data
        getUserDetails();
    }

    private void getUserDetails() {
        Doctor savedUser = session.getSavedUser();
        if (savedUser == null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) noSqlDatabase.getUser(
                    currentUser.getUid(),
                    swipeRefreshLayout,
                    (DataBaseCallback) data -> {
                        Doctor doctor = (Doctor) data;
                        fullName.setText(doctor.getName());
                        email.setText(doctor.getEmail());
                        phoneEditText.setText(doctor.getPhoneNumber());
                        addressTextEdit.setText(doctor.getAddress());
                        try {
                            Glide.with(this).load(doctor.getPhoto()).into(circleImageView);
                        } catch (Throwable ignore) {
                        }
                    });
        } else {
            fullName.setText(savedUser.getName());
            email.setText(savedUser.getEmail());
            phoneEditText.setText(savedUser.getPhoneNumber());
            addressTextEdit.setText(savedUser.getAddress());
            try {
                Glide.with(this).load(savedUser.getPhoto()).into(circleImageView);
            } catch (Throwable ignore) {
            }
        }

        //call get subscription
        //getSubDetails();
    }

    private void updateProfile() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) noSqlDatabase.updateUser(currentUser.getUid()
                , swipeRefreshLayout
                , fullName, email, phoneEditText, addressTextEdit);
    }

    private void bindView() {
        fullName = findViewById(R.id.profile_fullname);
        email = findViewById(R.id.profile_email);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        phoneEditText = findViewById(R.id.profile_phone);
        addressTextEdit = findViewById(R.id.profile_address);
        updateProfileButton = findViewById(R.id.profile_update_profile);
        //amountTextView = findViewById(R.id.profile_amount);
        //subscriptionTextEdit = findViewById(R.id.profile_subscription);
        //statusTextEdit = findViewById(R.id.profile_status);
        //payButton = findViewById(R.id.profile_payment);
        //receiptButton = findViewById(R.id.profile_payment_history);
        circleImageView = findViewById(R.id.profile_image);
        swipeRefreshLayout = findViewById(R.id.profile_swipe);
    }

    private void buttons() {

        updateProfileButton.setOnClickListener(v -> {
            updateProfile();
            Snackbar.make(v, "Updating profile...", Snackbar.LENGTH_SHORT).show();
        });
    }


    private void contactUs() {
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Help text", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.forum_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    //todo : this method is to be changed
    private void logout() {
        //set offline flag
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        new PostNoSqlDataBase(this).offline(uid);

        //logout
        // FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        //go to the main activity
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (new Session(this).getUserPay().equals(Session.PAY_OK))
            super.onBackPressed();
        else {

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
