package com.example.izi.memories;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest.Builder(MyWorker.class, 12, TimeUnit.HOURS);
        PeriodicWorkRequest workRequest = builder.build();
        // Then enqueue the recurring task:
        WorkManager.getInstance().enqueue(workRequest);
        return START_STICKY;
    }
}
