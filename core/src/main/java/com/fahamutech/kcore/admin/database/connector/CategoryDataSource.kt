package com.fahamutech.kcore.admin.database.connector

import com.fahamutech.kcore.admin.database.DataBaseCallback
import com.fahamutech.kcore.admin.model.Category

interface CategoryDataSource {
    fun createCategory(category: Category?, vararg callbacks: DataBaseCallback?)
    fun deleteCategory(docId: String?, vararg callbacks: DataBaseCallback?)
}