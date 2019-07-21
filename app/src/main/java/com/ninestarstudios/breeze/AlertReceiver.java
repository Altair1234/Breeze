package com.ninestarstudios.breeze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

public class AlertReceiver extends BroadcastReceiver {

    static MediaPlayer mediaPlayer = MainActivity.mediaPlayer;
    int flagOfTune = MainActivity.flagOfTune;
    private static final String TAG = "AlertReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm working fine!");

        if (flagOfTune == 0)
            mediaPlayer = MediaPlayer.create(context, R.raw.downpour_sound);
        else if (flagOfTune == 1)
            mediaPlayer = MediaPlayer.create(context, R.raw.knockerup_sound);
        else if (flagOfTune == 2)
            mediaPlayer = MediaPlayer.create(context, R.raw.cock_sound);
        else if (flagOfTune == 3)
            mediaPlayer = MediaPlayer.create(context, R.raw.singing_bowl_sound);

        mediaPlayer.start();

        mediaPlayer.setLooping(true);

        Handler handler = new Handler();
        Runnable stopPlaybackRun = new Runnable() {
            public void run() {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        };
        handler.postDelayed(stopPlaybackRun, 5 * 60 * 1000);

        Intent alarmIntent = new Intent("android.intent.action.MAIN");
        alarmIntent.setClass(context, StopAlarmActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }
}
