package com.fahamutech.kcore.admin.database.connector;

import com.fahamutech.kcore.admin.database.DataBaseCallback;
import com.fahamutech.kcore.admin.model.ICategory;

public interface IcategoryDataSource {
    void newCategory(ICategory category, DataBaseCallback... dataBaseCallbacks);

    void deleteCategory(DataBaseCallback... dataBaseCallbacks);
}
