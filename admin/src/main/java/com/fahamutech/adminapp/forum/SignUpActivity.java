package com.fahamutech.adminapp.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fahamutech.adminapp.R;
import com.fahamutech.adminapp.activities.MainActivity;
import com.fahamutech.adminapp.forum.database.UserDataSource;
import com.fahamutech.adminapp.forum.database.UserNoSqlDataBase;
import com.fahamutech.adminapp.forum.model.Doctor;
import com.fahamutech.adminapp.session.Session;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "ForumMainActivity";

    private Toolbar toolbar;
    private SignInButton signUpButton;
    private TextInputEditText phoneNumber;
    private TextInputEditText address;

    private int RC_SIGN_IN = 221;
    private FirebaseAuth mAuth;
    private UserDataSource noSqlDatabase;
    private MaterialDialog loginDialog;

    private Session session;
    private TextView textView;

    @Override
    protected void onStart() {
        checkIsLogin();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_activity_sign_up);
        bindView();

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("Sign Up");
        }

        //initiate database
        noSqlDatabase = new UserNoSqlDataBase(this);

        //initiate dialog
        initDialog();

        //start sign in
        signUpButton.setOnClickListener(v -> signIn());

        //initiate session
        session = new Session(this);
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar);
        signUpButton = findViewById(R.id.siginup);
        phoneNumber = findViewById(R.id.siginup_phone);
        address = findViewById(R.id.siginup_address);
        textView=findViewById(R.id.siginup_privacy);
        //textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onResume() {
        checkIsLogin();
        super.onResume();
    }

    private void checkIsLogin() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            goToMain();
        }
    }

    private void errorGetUser() {

    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * login to firebase using the google account created
     *
     * @param acct the google account
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {

                            //save user on the local


                            String photo;
                            if (user.getPhotoUrl() != null) {
                                photo = user.getPhotoUrl().toString();
                            } else photo = "";

                            //doctor model
                            Doctor doctor = new Doctor(
                                    user.getDisplayName(),
                                    user.getEmail(),
                                    mAuth.getUid(),
                                    photo,
                                    phoneNumber.getText().toString(),
                                    address.getText().toString(),
                                    Doctor.DOCTOR
                            );

                            //save user to local
                            session.saveUser(doctor);
                            //save pay to localhost


                            noSqlDatabase.createUser(doctor);
                            //hide login dialog
                            hideDialog();
                            //update profile
                            goToMain();
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithCredential:failure", task.getException());
                        errorGetUser();
                    }
                });
    }

    private void initDialog() {
        loginDialog = new MaterialDialog.Builder(this)
                .progress(true, 1)
                .negativeText("Cancel")
                .onNegative((dialog, which) -> {
                    hideDialog();
                    Snackbar.make(signUpButton,
                            "Process canceled, Try again", Snackbar.LENGTH_LONG).show();
                })
                .content("Please wait...")
                .canceledOnTouchOutside(false)
                .build();
    }

    private void hideDialog() {
        loginDialog.dismiss();
    }

    private void showDialog() {
        loginDialog.show();
    }

    private void signIn() {

        if (phoneNumber.getText().toString().isEmpty() || phoneNumber.getText().toString().length() < 10) {
            Snackbar.make(signUpButton, "Fill valid phone number...", Snackbar.LENGTH_SHORT).show();
        } else if (address.getText().toString().isEmpty()) {
            Snackbar.make(signUpButton, "Fill valid address...", Snackbar.LENGTH_SHORT).show();
        } else {
            // Configure Google Sign In
            mAuth = FirebaseAuth.getInstance();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    /**
     * called after sign in using google account
     *
     * @param requestCode the code requested
     * @param resultCode  the results code
     * @param data        from the intent return the result
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //start login dialog
                showDialog();

                //authenticate account to firebase
                if (account != null) firebaseAuthWithGoogle(account);
                else {
                    Toast.makeText(this, "Sign in failed, Try again", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }

            } catch (ApiException ignore) {
                // Google Sign In failed, update UI appropriately
                Snackbar.make(signUpButton,
                        "Google sign in canceled, Try again", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Sign in failed, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
