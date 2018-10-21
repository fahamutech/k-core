package com.fahamutech.adminapp.database.connector;

import com.fahamutech.adminapp.database.DataBaseCallback;
import com.fahamutech.adminapp.model.ITestimony;

public interface TestimonyDataSource {

    void createTestimony(ITestimony testimony, DataBaseCallback... callbacks);

    void deleteTestimony(String docId, DataBaseCallback... callbacks);
}
