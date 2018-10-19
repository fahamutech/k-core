package com.fahamutech.adminapp.forum.message;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MessageUtils {

    private static String TAG = "FCM SUBSCRIBE";
    private FirebaseMessaging firebaseMessaging;
    private FirebaseFunctions firebaseFunctions;

    public MessageUtils() {
        this.firebaseMessaging = FirebaseMessaging.getInstance();
        this.firebaseFunctions = FirebaseFunctions.getInstance();
    }

    public void subscribe(String docId) {
        firebaseMessaging.subscribeToTopic(docId)
                .addOnCompleteListener(task -> {
                    String msg = "IMEFANIKIWA++++++";
                    if (!task.isSuccessful()) {
                        msg = "IMESHINDWA++++++";
                    }
                    Log.e(TAG, msg);
                });

    }

    //data must contain notification and data for FCM and the topic
    public Task<String> sendFCMessage(String data) {
        return firebaseFunctions
                .getHttpsCallable("sendFcm")
                .call(data)
                .continueWith(
                        task -> {
                            // This continuation runs on either success or failure, but if the task
                            // has failed then getResult() will throw an Exception which will be
                            // propagated down.
                            HttpsCallableResult result = task.getResult();
                            if (result != null) {
                                return (String) result.getData();
                            } else return "error";
                        }
                );
    }

}
