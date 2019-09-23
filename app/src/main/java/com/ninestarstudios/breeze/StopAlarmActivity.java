package com.ninestarstudios.breeze;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class StopAlarmActivity extends AppCompatActivity {

    static MediaPlayer mediaPlayer, sirenPlayer;
    int flagOfTune;
    private static final String TAG = "AlertReceiver";
    boolean siren;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);
        Log.d("Main", "working till receiver");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        } else {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD +
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

        Log.d(TAG, "Alarm working fine!");

        sharedPreferences = this.getSharedPreferences("com.ninestarstudios.breeze", Context.MODE_PRIVATE);

        flagOfTune = sharedPreferences.getInt("flag", 0);
        boolean increasingVolume = sharedPreferences.getBoolean("increasing", false);
        final boolean vibrate = sharedPreferences.getBoolean("vibrating", false);

        siren = sharedPreferences.getBoolean("siren_confirm", false);

        if (flagOfTune == 0)
            mediaPlayer = MediaPlayer.create(this, R.raw.downpour_sound);
        else if (flagOfTune == 1)
            mediaPlayer = MediaPlayer.create(this, R.raw.knockerup_sound);
        else if (flagOfTune == 2)
            mediaPlayer = MediaPlayer.create(this, R.raw.cock_sound);
        else if (flagOfTune == 3)
            mediaPlayer = MediaPlayer.create(this, R.raw.singing_bowl_sound);

        mediaPlayer.start();
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        final float initialVolume = 0.2f;
        if (increasingVolume) {
            new CountDownTimer(54000, 6000) {
                public void onTick(long millisUntilFinished) {
                    if (mediaPlayer != null)
                        mediaPlayer.setVolume(initialVolume + 0.1f, initialVolume + 0.1f);
                }

                public void onFinish() {
                    finish();
                }
            }.start();
        }

        if (vibrate) {
            new CountDownTimer(54000, 500) {
                public void onTick(long millisUntilFinished) {
                    if (mediaPlayer != null)
                        vibrator.vibrate(500);
                }

                public void onFinish() {
                    finish();
                }
            }.start();
        }

        if (siren) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    sirenPlayer = MediaPlayer.create(StopAlarmActivity.this, R.raw.siren_sound);
                    sirenPlayer.start();
                    sirenPlayer.setVolume(1.0f, 1.0f);
                }
            };

            Handler h = new Handler();
            h.postDelayed(r, 120000);
        }

        Button mStopAlarm = findViewById(R.id.stop_alarm);
        mStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                if (sirenPlayer != null) {
                    sirenPlayer.stop();
                    sirenPlayer.release();
                    sirenPlayer = null;
                }
                if (vibrate)
                    vibrator.cancel();
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                if (!sharedPreferences.getBoolean("repeating", false)) {
                    sharedPreferencesEditor.putBoolean("alarm", false);
                    sharedPreferencesEditor.putString("alarm_time", "Set Alarm");
                    sharedPreferencesEditor.apply();
                }
                finish();
            }
        });
    }
}
