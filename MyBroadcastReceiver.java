package com.example.izi.memories;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // start service
        Intent intent2 = new Intent(context, MyService.class);
        context.startService(intent);
    }
}
