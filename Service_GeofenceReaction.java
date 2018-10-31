package com.example.izi.memories;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class Service_GeofenceReaction extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0")
                    .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                    .setContentTitle("ברוך הבא הביתה!")
                    .setContentText("איךך היה היום שלך?")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(7, mBuilder.build());
        }
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0")
                    .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                    .setContentTitle("יום טוב!")
                    .setContentText("תהנה בעבודה!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(6, mBuilder.build());
        }

        return START_STICKY;
    }
}
