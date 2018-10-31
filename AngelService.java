package com.example.izi.memories;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

public class AngelService extends Service {
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // POLL LOCATION
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3600000);// 1 hour intervals
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                }
            }
        };

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return START_STICKY;
        }
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                null);

        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        // WHAT (..start this receiver)
        Intent intentAlarm0400 = new Intent(this, BroadcastReceiver_SetGeofence.class);
        PendingIntent pendingIntent0400 = PendingIntent.getBroadcast(this, 0, intentAlarm0400, 0);
        // WHAT (..start this receiver)
        Intent intentAlarm1100 = new Intent(this, BroadcastReceiver_SetGeofence.class);
        PendingIntent pendingIntent1100 = PendingIntent.getBroadcast(this, 0, intentAlarm1100, 0);

        if(calendar.get(Calendar.HOUR_OF_DAY) < 4){

//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0")
//                    .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
//                    .setContentTitle("ANGEL SERVICE < 4")
//                    .setContentText("ANGEL SERVICE < 4")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            notificationManager.notify(0, mBuilder.build());

             // WHEN (..today at 04:00)
            Calendar calendar0400 = Calendar.getInstance();
            calendar0400.setTimeInMillis(System.currentTimeMillis());
            calendar0400.set(Calendar.HOUR_OF_DAY, 4);
            calendar0400.set(Calendar.MINUTE, 0);
            // COMBINE
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar0400.getTimeInMillis(), pendingIntent0400);

            // WHEN (..today at 11:00)
            Calendar calendar1100 = Calendar.getInstance();
            calendar1100.setTimeInMillis(System.currentTimeMillis());
            calendar1100.set(Calendar.HOUR_OF_DAY, 11);
            calendar1100.set(Calendar.MINUTE, 0);
            // COMBINE
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar0400.getTimeInMillis(), pendingIntent1100);


        }
        if(calendar.get(Calendar.HOUR_OF_DAY) < 11 && calendar.get(Calendar.HOUR_OF_DAY) >= 4){

//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0")
//                    .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
//                    .setContentTitle("ANGEL SERVICE  4 <>11")
//                    .setContentText("ANGEL SERVICE 4 <>11")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            notificationManager.notify(0, mBuilder.build());

             // WHEN (..now)
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTimeInMillis(System.currentTimeMillis());
            // COMBINE
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendarNow.getTimeInMillis(), pendingIntent0400);

             // WHEN (..today at 11:00)
            Calendar calendar1100 = Calendar.getInstance();
            calendar1100.setTimeInMillis(System.currentTimeMillis());
            calendar1100.set(Calendar.HOUR_OF_DAY, 11);
            calendar1100.set(Calendar.MINUTE, 0);
            // COMBINE
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar1100.getTimeInMillis(), pendingIntent1100);
        }
        if(calendar.get(Calendar.HOUR_OF_DAY) >= 11){
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0")
//                    .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
//                    .setContentTitle("ANGEL SERVICE > 11")
//                    .setContentText("ANGEL SERVICE > 11")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            notificationManager.notify(0, mBuilder.build());

            // WHEN (..tomorrow at 04:00)
            Calendar calendar0400 = Calendar.getInstance();
            calendar0400.setTimeInMillis(System.currentTimeMillis());
            calendar0400.add(Calendar.DAY_OF_MONTH, 1);
            calendar0400.set(Calendar.HOUR_OF_DAY, 4);
            calendar0400.set(Calendar.MINUTE, 0);
            // COMBINE
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar0400.getTimeInMillis(), pendingIntent0400);

            // WHEN (..tomorrow at 11:00)
            Calendar calendar1100 = Calendar.getInstance();
            calendar1100.setTimeInMillis(System.currentTimeMillis());
            calendar1100.add(Calendar.DAY_OF_MONTH, 1);
            calendar1100.set(Calendar.HOUR_OF_DAY, 11);
            calendar1100.set(Calendar.MINUTE, 0);
            // COMBINE
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar0400.getTimeInMillis(), pendingIntent1100);
        }

        return START_STICKY;
    }
}
