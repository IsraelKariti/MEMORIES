package com.example.izi.memories;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AngelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, AngelService.class);
        context.startService(intent);
    }
}
