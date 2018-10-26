package com.example.izi.memories;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyOneTimeWorker extends Worker {

    public MyOneTimeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // when this method gets called it is the first time 04:00 happend since the app was downloaded and opened
        // get the location

        // call MyPeriodicWorker to run every 24 hrs

        return Result.SUCCESS;
    }
}
