package com.example.advancedalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class AlarmManagement {
    public static int REQUEST_NOTIFICATION = 2413;
    public static void showAlert(Context context, Alarm alarm){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle(alarm.getName())
                .setContentText(alarm.getAlert().getShortDescription()).setStyle(new NotificationCompat
                        .BigTextStyle().bigText(alarm.getAlert().getDescription()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setSound(alarmSound)
                .setSmallIcon(R.drawable.ic_alarm_black_24dp).build();
    }
}
