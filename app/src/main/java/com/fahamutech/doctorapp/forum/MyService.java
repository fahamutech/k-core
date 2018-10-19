//package com.fahamutech.doctorapp.forum;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreSettings;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MyService extends Service {
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Map<String, Object> data = new HashMap<>();
//        data.put("test", "App Service Start to run ");
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(true)
//                .setSslEnabled(true)
//                .build();
//        FirebaseFirestore instance = FirebaseFirestore.getInstance();
//        instance.setFirestoreSettings(settings);
//        instance.collection("test").document().set(data);
//        return super.onStartCommand(intent, flags, startId);
//    }
//}
