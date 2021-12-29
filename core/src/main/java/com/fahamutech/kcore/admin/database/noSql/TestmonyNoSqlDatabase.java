package com.fahamutech.kcore.admin.database.noSql;

import android.content.Context;

import com.fahamutech.kcore.admin.database.DataBaseCallback;
import com.fahamutech.kcore.admin.database.connector.TestimonyDataSource;
import com.fahamutech.kcore.admin.model.ITestimony;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

public class TestmonyNoSqlDatabase extends NoSqlDatabase implements TestimonyDataSource {

    public TestmonyNoSqlDatabase(Context context) {
        super(context);
    }


    @Override
    public void createTestimony(ITestimony testimony, DataBaseCallback... callbacks) {
        DocumentReference document = firestore.collection("TESTIMONY").document();
        testimony.setId(document.getId());
        document.set(testimony, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    if (callbacks != null) callbacks[0].then("data");
                })
                .addOnFailureListener(e -> {
                    if (callbacks != null) callbacks[1].then("fail " + e.getLocalizedMessage());
                });
    }

    @Override
    public void deleteTestimony(String docId, DataBaseCallback... callbacks) {
        firestore.collection("TESTIMONY").document(docId).delete()
                .addOnSuccessListener(aVoid -> {
                    if (callbacks != null) callbacks[0].then("done");
                })
                .addOnFailureListener(e -> {
                    if (callbacks != null) callbacks[1].then("fail " + e.getLocalizedMessage());
                });
    }
}
