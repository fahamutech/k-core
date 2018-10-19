//package com.fahamutech.doctorapp.forum;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.widget.Toast;
//
//public class BootReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        String log = "Action: " + intent.getAction() + "\n" +
//                "URI: " + intent.toUri(Intent.URI_INTENT_SCHEME) + "\n";
//        String TAG = "BootReceiver**********";
//        Log.e(TAG, log);
//
//        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
//
//        context.startService(new Intent(context,MyService.class));
//    }
//}
