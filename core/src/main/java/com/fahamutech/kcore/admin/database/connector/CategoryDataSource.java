package com.fahamutech.kcore.admin.database.connector;

import com.fahamutech.kcore.admin.database.DataBaseCallback;
import com.fahamutech.kcore.admin.model.ICategory;

public interface CategoryDataSource {
    void createCategory(ICategory category, DataBaseCallback... callbacks);

    void deleteCategory(String docId, DataBaseCallback... callbacks);
}
