package com.example.izi.memories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

import androidx.work.WorkManager;

public class BroadcastReceiver_RemoveGeofence extends BroadcastReceiver {
    private GeofencingClient mGeofencingClient;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                .setContentTitle("BROADCAST RECEIVER REMOVE WORK")
                .setContentText("BROADCAST RECEIVER REMOVE WORK")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(5, mBuilder.build());

        ////////////////// remove all undone works ////////////////////////
        WorkManager.getInstance().cancelAllWork();

        ////////////////////////// calls itself tomorrow 11 AM////////////////////////////////
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //WHAT (..start this receiver)
        Intent intentAlarm = new Intent(context, BroadcastReceiver_RemoveGeofence.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intentAlarm, 0);
        // WHEN (..tomorrow at 11:00)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        // COMBINE
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }
}
