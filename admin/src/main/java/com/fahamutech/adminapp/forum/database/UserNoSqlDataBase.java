package com.fahamutech.adminapp.forum.database;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.fahamutech.adminapp.forum.model.Doctor;
import com.fahamutech.adminapp.session.Session;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;

public class UserNoSqlDataBase extends NoSqlDatabase implements UserDataSource {

    public UserNoSqlDataBase(Context context) {
        super(context);
    }

    /**
     * create the doctor to the database
     *
     * @param doctor the doctor object
     */
    @Override
    public void createUser(Doctor doctor) {
        Log.e(TAG, "start create doctor");
        DocumentReference document = firestore.collection(ForumC.FORUM_USER.name())
                .document(doctor.getEmail());
        document.set(doctor, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.e(TAG, "successful doctor added"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to add doctor : " + e.getMessage()));
    }

    @Override
    public void getUser(String userId, SwipeRefreshLayout swipeRefreshLayout,
                        DataBaseCallback... dataBaseCallbacks) {
        swipeRefreshLayout.setRefreshing(true);

        firestore.collection(ForumC.FORUM_USER.name())
                .whereEqualTo("id", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null) {
                        List<Doctor> doctors = queryDocumentSnapshots.toObjects(Doctor.class);
                        if (doctors.size() > 0) {
                            Doctor doctor = doctors.get(0);
                            if (dataBaseCallbacks != null) {
                                dataBaseCallbacks[0].then(doctor);
                            }
                        }
                    }
                    swipeRefreshLayout.setRefreshing(false);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "failed to get user details, reason : " + e);
                    swipeRefreshLayout.setRefreshing(false);
                });
    }


    @Override
    public void updateUser(String userId, SwipeRefreshLayout swipeRefreshLayout, View... view) {
        swipeRefreshLayout.setRefreshing(true);
        TextInputEditText name = (TextInputEditText) view[0];
        TextInputEditText email = (TextInputEditText) view[1];
        TextInputEditText phone = (TextInputEditText) view[2];
        TextInputEditText address = (TextInputEditText) view[3];

        Session session = new Session(context);
        Doctor savedUser = session.getSavedUser();
        savedUser.setPhoneNumber(phone.getText().toString());
        savedUser.setAddress(address.getText().toString());
        session.saveUser(savedUser);

        HashMap<String, Object> data = new HashMap<>();
        data.put("phoneNumber", phone.getText().toString());
        data.put("address", address.getText().toString());
        firestore.collection(ForumC.FORUM_USER.name())
                .document(email.getText().toString())
                .update(data)
                .addOnSuccessListener(aVoid -> {
                    Snackbar.make(email, "Profile updated", Snackbar.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to update profile, reason : " + e);
                    swipeRefreshLayout.setRefreshing(false);
                });
    }


}
