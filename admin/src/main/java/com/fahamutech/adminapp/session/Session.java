package com.fahamutech.adminapp.session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    public static String PAY_OK = "AkjgaoHFuf687V";
    public static String PAY_NOT_OK = "uy5rhgJFFjh";
    public static String PAY_D = "T74r74jhkjklh";

    private SharedPreferences sharedPreferences;
    private String CAT = "_category_";
    private String TITLE = "_title_";
    private String PATIENT = "_patient_";
    private String PAY = "_payment_";
    private String CATEGORIES="_category_list_";

    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences("jhsffsaiuda", Context.MODE_PRIVATE);
    }

    public void saveLastCategory(String category) {
        sharedPreferences.edit().putString(CAT, category).apply();
    }

    public void saveLastTitle(String title) {
        sharedPreferences.edit().putString(TITLE, title).apply();
    }

    public String getLastCategory() {
        return sharedPreferences.getString(CAT, "");
    }

    public String getLastTitle() {
        return sharedPreferences.getString(TITLE, "");
    }
}
