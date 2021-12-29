package com.fahamutech.kcoreuser.database.noSql;

import android.content.Context;

import com.fahamutech.kcoreuser.database.DataBaseCallback;
import com.fahamutech.kcoreuser.database.connector.CategoryDataSource;
import com.fahamutech.kcoreuser.model.ICategory;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

public class CategoryNoSqlDatabase extends NoSqlDatabase implements CategoryDataSource {

    public CategoryNoSqlDatabase(Context context) {
        super(context);
    }

    @Override
    public void createCategory(ICategory category, DataBaseCallback... callbacks) {
        DocumentReference doc = firestore.collection("CATEGORY").document();
        category.setId(doc.getId());
        doc.set(category, SetOptions.merge())
                .addOnSuccessListener(aVoid -> callbacks[0].then("done"))
                .addOnFailureListener(e -> callbacks[1].then(e.getLocalizedMessage()));
    }

    @Override
    public void deleteCategory(String docId, DataBaseCallback... callbacks) {
        firestore.collection("CATEGORY").document(docId).delete()
                .addOnSuccessListener(aVoid -> {
                    callbacks[0].then("done");
                })
                .addOnFailureListener(e -> {
                    callbacks[1].then(e.getLocalizedMessage());
                });
    }
}
