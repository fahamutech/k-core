package com.fahamutech.kcoreuser.database.connector;

import com.fahamutech.kcoreuser.database.DataBaseCallback;
import com.fahamutech.kcoreuser.model.ICategory;

public interface CategoryDataSource {
    void createCategory(ICategory category, DataBaseCallback... callbacks);

    void deleteCategory(String docId, DataBaseCallback... callbacks);
}
