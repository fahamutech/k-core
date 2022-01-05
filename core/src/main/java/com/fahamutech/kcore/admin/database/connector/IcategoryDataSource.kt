package com.fahamutech.kcore.admin.database.connector

import com.fahamutech.kcore.admin.database.DataBaseCallback
import com.fahamutech.kcore.admin.model.ICategory

interface IcategoryDataSource {
    fun newCategory(category: ICategory?, vararg dataBaseCallbacks: DataBaseCallback?)
    fun deleteCategory(vararg dataBaseCallbacks: DataBaseCallback?)
}