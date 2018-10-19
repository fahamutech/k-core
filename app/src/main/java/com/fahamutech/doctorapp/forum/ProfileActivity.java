package com.fahamutech.doctorapp.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.fahamutech.doctorapp.R;
import com.fahamutech.doctorapp.activities.MainActivity;
import com.fahamutech.doctorapp.forum.database.DataBaseCallback;
import com.fahamutech.doctorapp.forum.database.PostNoSqlDataBase;
import com.fahamutech.doctorapp.forum.database.UserDataSource;
import com.fahamutech.doctorapp.forum.database.UserNoSqlDataBase;
import com.fahamutech.doctorapp.forum.model.Patient;
import com.fahamutech.doctorapp.forum.model.UserSubscription;
import com.fahamutech.doctorapp.session.Session;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    // private FloatingActionButton fab;
    private TextInputEditText phoneEditText;
    private TextInputEditText fullName;
    private TextInputEditText email;
    private TextInputEditText addressTextEdit;
    private Button updateProfileButton;
    private TextView amountTextView;
    private TextInputEditText subscriptionTextEdit;
    private TextInputEditText statusTextEdit;
    private Button payButton;
    private Button receiptButton;
    private CircleImageView circleImageView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserDataSource noSqlDatabase;
    private Session session;
    private MaterialDialog materialDialog;

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
        //contactUs();

        //listener
        buttons();

        //user data
        getUserDetails();


        //subscription details
        getSubDetails();


    }

    private void getUserDetails() {
        Patient savedUser = session.getSavedUser();
        if (savedUser == null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) noSqlDatabase.getUser(
                    currentUser.getUid(),
                    swipeRefreshLayout,
                    (DataBaseCallback) data -> {
                        Patient patient = (Patient) data;
                        fullName.setText(patient.getName());
                        email.setText(patient.getEmail());
                        phoneEditText.setText(patient.getPhoneNumber());
                        addressTextEdit.setText(patient.getAddress());
                        try {
                            Glide.with(this).load(patient.getPhoto()).into(circleImageView);
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

    private void getSubDetails() {
        initDialog();
        showDialog();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            noSqlDatabase.getUserSubscription(currentUser.getEmail(), swipeRefreshLayout,
                    (DataBaseCallback) data -> {
                        UserSubscription subscription = (UserSubscription) data;
                        if (subscription != null) {
                            long l = Long.parseLong(subscription.getEnd());
                            if (l > System.currentTimeMillis())
                                accActive(subscription);
                            else accExpire();
                            hideDialog();
                        } else {
                            accExpire();
                            hideDialog();
                        }
                    }
            );
        }
    }

    private void accActive(UserSubscription subscription) {
        amountTextView.setText(subscription.getAmount());
        subscriptionTextEdit.setText(R.string.vip);
        statusTextEdit.setText(R.string.premium);
        //hide pay
        payButton.setVisibility(View.GONE);
        //update pay status
        session.userPay(Session.PAY_OK);
    }

    private void accExpire() {
        amountTextView.setText("0");
        subscriptionTextEdit.setText(R.string.free);
        statusTextEdit.setText(R.string.account_expire);
        //show pay
        payButton.setVisibility(View.VISIBLE);
        //update pay status
        session.userPay(Session.PAY_NOT_OK);
    }

    private void initDialog() {
        materialDialog = new MaterialDialog.Builder(this)
                .progress(true, 1)
                .content("Checking payment...")
                .canceledOnTouchOutside(false)
                .build();
    }

    private void hideDialog() {
        materialDialog.dismiss();
    }

    private void showDialog() {
        materialDialog.show();
    }

    private void bindView() {
        fullName = findViewById(R.id.profile_fullname);
        email = findViewById(R.id.profile_email);
        toolbar = findViewById(R.id.toolbar);
        //fab = findViewById(R.id.fab);
        phoneEditText = findViewById(R.id.profile_phone);
        addressTextEdit = findViewById(R.id.profile_address);
        updateProfileButton = findViewById(R.id.profile_update_profile);
        amountTextView = findViewById(R.id.profile_amount);
        subscriptionTextEdit = findViewById(R.id.profile_subscription);
        statusTextEdit = findViewById(R.id.profile_status);
        payButton = findViewById(R.id.profile_payment);
        receiptButton = findViewById(R.id.profile_payment_history);
        circleImageView = findViewById(R.id.profile_image);
        swipeRefreshLayout = findViewById(R.id.profile_swipe);
    }

    private void buttons() {
        receiptButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ReceiptsActivity.class));
        });

        payButton.setOnClickListener(v -> {
            //payDialog();
            Intent intent = new Intent(this, PayActivity.class);
            String amount = "10000";
            intent.putExtra("_amount", amount);
            startActivity(intent);
        });

        updateProfileButton.setOnClickListener(v -> {
            updateProfile();
            Snackbar.make(v, "Updating profile...", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void payDialog() {
        new MaterialDialog.Builder(this)
                .title("Choose Package")
                .customView(R.layout.forum_checkout, true)
                .positiveText("Pay")
                .negativeText("Cancel")
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .onNegative((dialog, which) -> {
                    Snackbar.make(payButton,
                            "PaymentModel Canceled", Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .onPositive((dialog, which) -> {
                    View customView = dialog.getCustomView();
                    if (customView != null) {
                        String amount;
                        Intent intent = new Intent(this, PayActivity.class);
                        RadioButton month = customView.findViewById(R.id.pay_monthly);
                        RadioButton sixMonth = customView.findViewById(R.id.pay_six_month);
                        RadioButton year = customView.findViewById(R.id.pay_twelve_month);

                        if (month.isChecked()) {
                            amount = "10000";
                            intent.putExtra("_amount", amount);
                            dialog.dismiss();
                            startActivity(intent);
                            //Snackbar.make(customView, "Month", Snackbar.LENGTH_SHORT).show();
                        } else if (sixMonth.isChecked()) {
                            amount = "50000";
                            intent.putExtra("_amount", amount);
                            dialog.dismiss();
                            startActivity(intent);
                            //Snackbar.make(customView, "6 month", Snackbar.LENGTH_SHORT).show();
                        } else if (year.isChecked()) {
                            amount = "100000";
                            intent.putExtra("_amount", amount);
                            dialog.dismiss();
                            startActivity(intent);
                            //Snackbar.make(customView, "year", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(customView, "Choose package first", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    private void contactUs() {
//        fab.setOnClickListener(view ->
//                Snackbar.make(view, "Help text", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show());
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
