package com.fahamutech.kcoreuser.database.connector;

import com.fahamutech.kcoreuser.database.DataBaseCallback;
import com.fahamutech.kcoreuser.model.ICategory;

public interface IcategoryDataSource {
    void newCategory(ICategory category, DataBaseCallback... dataBaseCallbacks);

    void deleteCategory(DataBaseCallback... dataBaseCallbacks);
}
