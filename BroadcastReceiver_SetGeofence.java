package com.example.izi.memories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Calendar;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class BroadcastReceiver_SetGeofence extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "0")
//                .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
//                .setContentTitle("BROADCAST RECEIVER CALL WORK")
//                .setContentText("BROADCAST RECEIVER CALL WORK")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(1, mBuilder.build());

        // schedule work for setting GeoFence - ASAP!
        Constraints myConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(WorkerSetGeofence.class)
                .setConstraints(myConstraints)
                .build();
        WorkManager.getInstance().enqueue(work);

        ////////////////////////// calls itself tomorrow 4 AM////////////////////////////////
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // WHAT (..start this receiver)
        Intent intentAlarm = new Intent(context, BroadcastReceiver_SetGeofence.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intentAlarm, 0);
        // WHEN (..tomorrow at 04:00)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 0);
        // COMBINE
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }
}
