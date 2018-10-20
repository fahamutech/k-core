package com.fahamutech.adminapp.database.connector;

import com.fahamutech.adminapp.database.DataBaseCallback;
import com.fahamutech.adminapp.model.ICategory;

public interface IcategoryDataSource {
    void newCategory(ICategory category, DataBaseCallback... dataBaseCallbacks);

    void deleteCategory(DataBaseCallback... dataBaseCallbacks);
}
