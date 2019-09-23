package com.ninestarstudios.breeze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent alarmIntent = new Intent("android.intent.action.MAIN");
        alarmIntent.setClass(context, StopAlarmActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
        Log.d("Main", "working till receiver");
    }
}
