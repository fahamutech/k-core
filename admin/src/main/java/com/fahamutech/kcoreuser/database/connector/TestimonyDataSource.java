package com.fahamutech.kcoreuser.database.connector;

import com.fahamutech.kcoreuser.database.DataBaseCallback;
import com.fahamutech.kcoreuser.model.ITestimony;

public interface TestimonyDataSource {

    void createTestimony(ITestimony testimony, DataBaseCallback... callbacks);

    void deleteTestimony(String docId, DataBaseCallback... callbacks);
}
