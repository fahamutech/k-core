package com.fahamutech.kcore.user.session

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val CAT = "_category_"
    private val TITLE = "_title_"
    private val PATIENT = "_patient_"
    private val PAY = "_payment_"
    fun saveLastCategory(category: String?) {
        sharedPreferences.edit().putString(CAT, category).apply()
    }

    fun saveLastTitle(title: String?) {
        sharedPreferences.edit().putString(TITLE, title).apply()
    }

    val lastCategory: String?
        get() = sharedPreferences.getString(CAT, "")
    val lastTitle: String?
        get() = sharedPreferences.getString(TITLE, "")

    init {
        sharedPreferences = context.getSharedPreferences("jhsffsaiuda", Context.MODE_PRIVATE)
    }
}