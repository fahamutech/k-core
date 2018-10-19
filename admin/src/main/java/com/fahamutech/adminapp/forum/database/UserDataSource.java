package com.fahamutech.adminapp.forum.database;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.fahamutech.adminapp.forum.model.Doctor;

public interface UserDataSource {
    void createUser(Doctor doctor);

    void getUser(String userId, SwipeRefreshLayout swipeRefreshLayout, DataBaseCallback... dataBaseCallback);

    void updateUser(String userId, SwipeRefreshLayout swipeRefreshLayout, View... views);

}
