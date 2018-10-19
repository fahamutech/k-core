package com.fahamutech.adminapp.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.fahamutech.adminapp.forum.model.Doctor;
import com.google.gson.Gson;

public class Session {

    public static String PAY_OK = "AkjgaoHFuf687V";
    public static String PAY_NOT_OK = "uy5rhgJFFjh";
    public static String PAY_D = "T74r74jhkjklh";

    private SharedPreferences sharedPreferences;
    private String CAT = "_category_";
    private String TITLE = "_title_";
    private String PATIENT = "_patient_";
    private String PAY = "_payment_";

    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences("jhsffsaiuda", Context.MODE_PRIVATE);
    }

    public void userPay(String pay) {
        sharedPreferences.edit().putString(PAY, pay).apply();
    }

    public String getUserPay() {
        return sharedPreferences.getString(PAY, PAY_D);
    }

    public void saveUser(Doctor doctor) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Gson gson = new Gson();
        String s = gson.toJson(doctor);
        edit.putString(PATIENT, s).apply();
    }

    public Doctor getSavedUser() {
        String string = sharedPreferences.getString(PATIENT, "");
        if (string.isEmpty()) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(string, Doctor.class);
        }
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
