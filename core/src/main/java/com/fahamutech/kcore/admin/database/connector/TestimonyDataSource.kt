package com.fahamutech.kcore.admin.database.connector;

import com.fahamutech.kcore.admin.database.DataBaseCallback;
import com.fahamutech.kcore.admin.model.ITestimony;

public interface TestimonyDataSource {

    void createTestimony(ITestimony testimony, DataBaseCallback... callbacks);

    void deleteTestimony(String docId, DataBaseCallback... callbacks);
}
