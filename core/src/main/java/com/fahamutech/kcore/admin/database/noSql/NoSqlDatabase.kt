package com.fahamutech.kcore.admin.database.noSql;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public abstract class NoSqlDatabase {

    FirebaseFirestore firestore;
    protected Context context;

    NoSqlDatabase(Context context) {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setSslEnabled(true)
                .build();
        this.firestore = FirebaseFirestore.getInstance();
        this.firestore.setFirestoreSettings(settings);
        this.context = context;
    }


}
