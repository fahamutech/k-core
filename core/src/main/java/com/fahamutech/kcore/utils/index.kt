package com.fahamutech.kcore.utils

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.lang.Exception


fun getKCoreFirebaseApp(context: Context): FirebaseApp {
    return try {
        FirebaseApp.getInstance("kcore")
    } catch (e: Exception) {
        val options = FirebaseOptions.Builder()
            .setProjectId("doctor-fahamutech")
            .setApplicationId("1:1040903580466:android:e09156f8710fd141")
            .setApiKey("AIzaSyDqob6T4rD95zrYpVoddTtfxt-zZgHuYhg")
            .setDatabaseUrl("https://doctor-fahamutech.firebaseio.com")
            .setStorageBucket("doctor-fahamutech.appspot.com")
            .build()
        FirebaseApp.initializeApp(context, options, "kcore")
    }
}

//<string name="default_web_client_id" translatable="false">1040903580466-cms8p4kqes0f4nau968ljmhsvi4sipnf.apps.googleusercontent.com</string>
//<string name="firebase_database_url" translatable="false">https://doctor-fahamutech.firebaseio.com</string>
//<string name="gcm_defaultSenderId" translatable="false">1040903580466</string>
//<string name="google_api_key" translatable="false">AIzaSyDqob6T4rD95zrYpVoddTtfxt-zZgHuYhg</string>
//<string name="google_app_id" translatable="false">1:1040903580466:android:e09156f8710fd141</string>
//<string name="google_crash_reporting_api_key" translatable="false">AIzaSyDqob6T4rD95zrYpVoddTtfxt-zZgHuYhg</string>
//<string name="google_storage_bucket" translatable="false">doctor-fahamutech.appspot.com</string>
//<string name="project_id" translatable="false">doctor-fahamutech</string>