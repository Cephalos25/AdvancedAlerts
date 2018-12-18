package com.example.advancedalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

public class AlarmMaker {
    static void eraseAlarms(Context context, List<Alarm> alarmList){
        for (Alarm alarm : alarmList) {
            Intent notifyIntent = new Intent(context, PutNotificationReceiver.class);
            notifyIntent.putExtra("alarm", alarm);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }
    static void placeAlarms(Context context, List<Alarm> alarmList){
        for (Alarm alarm : alarmList) {
            Intent notifyIntent = new Intent(context, PutNotificationReceiver.class);
            notifyIntent.putExtra("alarm", alarm);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            ZoneId userTimeZone = TimeZone.getDefault().toZoneId();
            ZonedDateTime zonedAlarmTime = ZonedDateTime.of(alarm.getEventDate(), userTimeZone);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, zonedAlarmTime.toInstant().toEpochMilli(),
                    pendingIntent);
        }
    }
}
