package com.example.advancedalarm;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class PutNotificationService extends IntentService {
    public PutNotificationService() {
        super("PutNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            byte[] alarmBytes = intent.getByteArrayExtra("alarm");
            int notificationID = intent.getIntExtra("notification ID", 1023);
            Alarm alarm = ParcelableUtil.unmarshall(alarmBytes, Alarm.CREATOR);
            Notification notification;
            if(alarm.getAlert().isImportant()){
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                        AlarmViewActivity.CHANNEL_TWO_ID).setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp).setContentTitle(alarm.getName())
                        .setContentText(alarm.getAlert().getShortDescription())
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(alarm.getAlert().getDescription()));
                notification = mBuilder.build();
            } else {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,
                        AlarmViewActivity.CHANNEL_ONE_ID).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp).setContentTitle(alarm.getName())
                        .setContentText(alarm.getAlert().getShortDescription())
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(alarm.getAlert().getDescription()));
                notification = mBuilder.build();
            }
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(notificationID, notification);
        }
    }
}
