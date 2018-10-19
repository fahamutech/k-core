package com.fahamutech.doctorapp.forum.database;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.fahamutech.doctorapp.forum.model.Patient;

public interface UserDataSource {
    void createUser(Patient patient);

    void getUser(String userId, SwipeRefreshLayout swipeRefreshLayout, DataBaseCallback... dataBaseCallback);

    void getUserSubscription(String docId, SwipeRefreshLayout swipeRefreshLayout, DataBaseCallback... dataBaseCallback);

    void updateUser(String userId, SwipeRefreshLayout swipeRefreshLayout, View... views);

    void getReceipts(String docId,SwipeRefreshLayout swipeRefreshLayout,DataBaseCallback... dataBaseCallbacks);
}
