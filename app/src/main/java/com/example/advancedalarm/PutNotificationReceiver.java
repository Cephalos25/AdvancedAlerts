package com.example.advancedalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PutNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        byte[] bytes = intent.getByteArrayExtra("alarm");
        int notificationID = intent.getIntExtra("notification ID", 1023);
        Intent serviceIntent = new Intent(context, PutNotificationService.class);
        serviceIntent.putExtra("alarm", bytes);
        serviceIntent.putExtra("notification ID", notificationID);
        context.startService(serviceIntent);
    }
}
