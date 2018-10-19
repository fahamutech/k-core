package com.fahamutech.doctorapp.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fahamutech.doctorapp.R;
import com.fahamutech.doctorapp.forum.database.PayDataSource;
import com.fahamutech.doctorapp.forum.database.PaymentNoSqlDataBase;
import com.fahamutech.doctorapp.forum.model.PaymentModel;
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

    private Toolbar toolbar;
    private CardView confirmCard;
    // private CardView callCard;
    private TextInputEditText numberEdit;
    private TextInputEditText messageEdit;
    private Button confirmButton;
    //private Button callButton;
    private PayDataSource payDataSource;
    private String uid;
    private MaterialDialog dialog;

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
            //database
            uid = firebaseAuth.getCurrentUser().getUid();
            payDataSource = new PaymentNoSqlDataBase(this);
            initUI();
        }
    }

    private void refreshStatus(){

    }

    private void initUI() {
        iniDialog();

        confirmButton.setOnClickListener(v -> {
            if (numberEdit.getText().toString().isEmpty()
                    || numberEdit.getText().toString().length() < 10
                    || numberEdit.getText().toString().contains("!#$%&'()~<>?_*+")) {
                numberEdit.requestFocus();
                Snackbar.make(v, "Enter valid number", Snackbar.LENGTH_SHORT).show();
            } else if (messageEdit.getText().toString().isEmpty()) {
                messageEdit.requestFocus();
                Snackbar.make(v, "Paste the message of payment...", Snackbar.LENGTH_SHORT).show();
            } else {
                showDialog();
                payDataSource.pay(
                        new PaymentModel(
                                "10000",
                                messageEdit.getText().toString(),
                                numberEdit.getText().toString(),
                                System.currentTimeMillis(),
                                uid,
                                false
                        ),
                        data -> {
                            //if success
                            hideDialog();
                        },
                        data -> {
                            //if fails
                            hideDialog();
                            Log.e("TAG**PAY ", (String) data);
                        }
                );
            }
        });

    }

    private void iniDialog() {
        dialog = new MaterialDialog.Builder(this)
                .progress(true, 1)
                .title("Payment")
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .negativeText("Cancel")
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
                })
                .content("Please wait...")
                .build();
    }

    private void showDialog() {
        dialog.show();
    }

    private void hideDialog() {
        dialog.hide();
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        confirmButton = findViewById(R.id.pay_confirm);
        confirmCard = findViewById(R.id.pay_card_confirm);
        //callCard = findViewById(R.id.pay_card_call);
        numberEdit = findViewById(R.id.pay_number);
        messageEdit = findViewById(R.id.pay_message);
        // callButton = findViewById(R.id.pay_confirm_call);
    }

    @Override
    protected void onDestroy() {
        dialog.dismiss();
        super.onDestroy();
    }
}
