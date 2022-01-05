package com.fahamutech.kcore.admin.database.connector

import com.fahamutech.kcore.admin.database.DataBaseCallback
import com.fahamutech.kcore.admin.model.ITestimony

interface TestimonyDataSource {
    fun createTestimony(testimony: ITestimony?, vararg callbacks: DataBaseCallback?)
    fun deleteTestimony(docId: String?, vararg callbacks: DataBaseCallback?)
}