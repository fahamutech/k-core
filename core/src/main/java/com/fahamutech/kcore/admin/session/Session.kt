package com.fahamutech.kcore.admin.session

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("jhsffsaiuda", Context.MODE_PRIVATE)
    private val CAT = "_category_"
    private val TITLE = "_title_"
    private val PATIENT = "_patient_"
    private val PAY = "_payment_"
    private val CATEGORIES = "_category_list_"
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

    companion object {
        var PAY_OK = "AkjgaoHFuf687V"
        var PAY_NOT_OK = "uy5rhgJFFjh"
        var PAY_D = "T74r74jhkjklh"
    }

}