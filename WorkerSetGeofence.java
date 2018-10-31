package com.example.izi.memories;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executor;

import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkerSetGeofence extends Worker {
    Context mContext;
    private GeofencingClient mGeofencingClient;
    private FusedLocationProviderClient mFusedLocationClient;
    Location mLocation;

    public WorkerSetGeofence(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "0")
//                .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
//                .setContentTitle("WORK")
//                .setContentText("WORK")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
//        notificationManager.notify(2, mBuilder.build());

        // get current location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return Result.FAILURE;
        }
        Task task = mFusedLocationClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Logic to handle location object
                    mLocation = location;
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "0")
//                            .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
//                            .setContentTitle("success TO GET LOCATION")
//                            .setContentText("success TO GET LOCATION")
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
//                    notificationManager.notify(3, mBuilder.build());

                    setGeofence();
                }
            }
        });

        return Result.SUCCESS;
    }

    private void setGeofence() {
        // get currrent time
        Calendar calendarCurrent = Calendar.getInstance();

        // get time at 10 PM
        Calendar calendar22PM = Calendar.getInstance();
        calendar22PM.set(Calendar.HOUR_OF_DAY, 22);
        calendar22PM.set(Calendar.MINUTE, 0);
        calendar22PM.set(Calendar.SECOND, 0);

        // get time diff
        long diffUntil22PM = calendar22PM.getTimeInMillis() - calendarCurrent.getTimeInMillis();

        ////////////////// SET GEOFENCE //////////////////////////////
        mGeofencingClient = LocationServices.getGeofencingClient(mContext);
        ArrayList<Geofence> mGeofenceList = new ArrayList<Geofence>();
        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("1")
                .setCircularRegion(
                        mLocation.getLatitude(),
                        mLocation.getLongitude(),
                        100
                )
                .setExpirationDuration(diffUntil22PM)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder()
                .setInitialTrigger(0)
                .addGeofences(mGeofenceList);

        Intent intent = new Intent(mContext, Service_GeofenceReaction.class);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGeofencingClient.addGeofences(builder.build(), pendingIntent);
    }
}
