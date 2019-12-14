package com.ninestarstudios.breeze;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Calendar;

public class StopAlarmActivity extends AppCompatActivity {

    static MediaPlayer mediaPlayer;
    ImageView stopBackground;
    int mFlagOfTune, mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);
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

        SharedPref.init(getApplicationContext());

        Button mStopAlarm = findViewById(R.id.stop_alarm);
        stopBackground = findViewById(R.id.stop_background);

        mFlagOfTune = SharedPref.read(SharedPref.FLAG_OF_TUNE, 0);
        mBackground = SharedPref.read(SharedPref.BACKGROUND, R.drawable.background_1);
        boolean mIncreasingVolume = SharedPref.read(SharedPref.INCREASING, false);
        final boolean mVibrate = SharedPref.read(SharedPref.VIBRATING, true);

        stopBackground.setImageResource(mBackground);

        if (mFlagOfTune == 0)
            mediaPlayer = MediaPlayer.create(this, R.raw.downpour_sound);
        else if (mFlagOfTune == 1)
            mediaPlayer = MediaPlayer.create(this, R.raw.knockerup_sound);
        else if (mFlagOfTune == 2)
            mediaPlayer = MediaPlayer.create(this, R.raw.cock_sound);
        else if (mFlagOfTune == 3)
            mediaPlayer = MediaPlayer.create(this, R.raw.singing_bowl_sound);

        mediaPlayer.start();
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final float INITIAL_VOLUME = 0.2f;
        if (mIncreasingVolume) {
            new CountDownTimer(54000, 6000) {
                public void onTick(long millisUntilFinished) {
                    if (mediaPlayer != null)
                        mediaPlayer.setVolume(INITIAL_VOLUME + 0.1f, INITIAL_VOLUME + 0.1f);
                }

                public void onFinish() {
                    finish();
                }
            }.start();
        }

        if (mVibrate) {
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

        mStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPref.read(SharedPref.REPEATING, false)) {
                    SharedPref.write(SharedPref.ALARM_KEY, false);
                    SharedPref.write(SharedPref.ALARM_TIME, "Set Alarm");
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(SharedPref.read(SharedPref.TIME_IN_MILLIS + 24 * 60 * 60 * 1000, 0));
                    Intent intent = new Intent(StopAlarmActivity.this, AlertReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(StopAlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                if (mVibrate)
                    vibrator.cancel();
                finish();
            }
        });
    }
}
