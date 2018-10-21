package com.fahamutech.adminapp.database.connector;

import com.fahamutech.adminapp.database.DataBaseCallback;
import com.fahamutech.adminapp.model.ICategory;

public interface CategoryDataSource {
    void createCategory(ICategory category, DataBaseCallback... callbacks);

    void deleteCategory(String docId, DataBaseCallback... callbacks);
}
