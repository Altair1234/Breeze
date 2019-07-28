package com.ninestarstudios.breeze;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

//import static android.view.Gravity.CENTER;

public class MainActivity extends AppCompatActivity {

    static Button mSetTime, mCancelButton;
    LinearLayout mainActivityLayout;
    private static final String TAG = "MainActivity";
    SharedPreferences mPreferences;
    static boolean alarmSet, increasingVolume;
    private final String ALARM_KEY = "alarm", INCREASING_KEY = "increasing";
    Switch mSetIncreasing;
    TextView mTuneDetail;
    static MediaPlayer mediaPlayer;
    int[] isCheckedDay = {0, 0, 0, 0, 0, 0, 0};
    Button mSunday, mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday, mSetAlarm;
    LinearLayout mRepeatDays1, mRepeatDays2;
    static LinearLayout mTunes;
    ImageButton mDownpour, mKnockerUp, mCock, mBonsho;
    static CheckBox repeatAlarmCheckBox;
    Long dateInMillis;
    static int userHour = 0, userMinute = 0;
    static boolean repeatAlarm = false;
    static int flagOfTune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDayNightTheme();

        mainActivityLayout = findViewById(R.id.main_activity_layout);
        mSetTime = findViewById(R.id.set_time);
        mSetIncreasing = findViewById(R.id.increasing_volume);
        mCancelButton = findViewById(R.id.cancel_button);
        mRepeatDays1 = findViewById(R.id.repeat_days_1);
        mRepeatDays2 = findViewById(R.id.repeat_days_2);
        mTunes = findViewById(R.id.tunes);
        mTuneDetail = findViewById(R.id.tune_details);
        mSunday = findViewById(R.id.sunday);
        mMonday = findViewById(R.id.monday);
        mTuesday = findViewById(R.id.tuesday);
        mWednesday = findViewById(R.id.wednesday);
        mThursday = findViewById(R.id.thursday);
        mFriday = findViewById(R.id.friday);
        mSaturday = findViewById(R.id.saturday);
        mDownpour = findViewById(R.id.downpour);
        mKnockerUp = findViewById(R.id.knockerup);
        mCock = findViewById(R.id.cock);
        mBonsho = findViewById(R.id.bonsho);
        repeatAlarmCheckBox = findViewById(R.id.repeat_alarm);
        mSetAlarm = findViewById(R.id.set_alarm);

        String sharedPrefFile = "com.deproc.breeze";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        alarmSet = mPreferences.getBoolean(ALARM_KEY, false);
        if (alarmSet)
            mSetTime.setText("");
        else
            mSetTime.setText("Set Alarm");
        increasingVolume = mPreferences.getBoolean(INCREASING_KEY, false);
        mSetIncreasing.setChecked(increasingVolume);

        mSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmDialog alarmDialog = new AlarmDialog();
                alarmDialog.show(getSupportFragmentManager(), "AlarmDialog");
            }
        });

        mSetIncreasing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "Volume of alarm will increase until stopped", Toast.LENGTH_LONG).show();
                    increasingVolume = true;
                } else {
                    increasingVolume = false;
                }
            }
        });

        repeatAlarmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRepeatDays1.setVisibility(View.VISIBLE);
                    mRepeatDays2.setVisibility(View.VISIBLE);
                } else {
                    mRepeatDays1.setVisibility(View.GONE);
                    mRepeatDays2.setVisibility(View.GONE);
                }
            }
        });

        mSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[0] == 0) {
                    isCheckedDay[0] = 1;
                    setAlarm(1, repeatAlarm);
                    mSunday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[0] == 1) {
                    isCheckedDay[0] = 0;
                    cancelAlarm(1);
                    mSunday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[1] == 0) {
                    isCheckedDay[1] = 1;
                    setAlarm(2, repeatAlarm);
                    mMonday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[1] == 1) {
                    isCheckedDay[1] = 0;
                    cancelAlarm(2);
                    mMonday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[2] == 0) {
                    isCheckedDay[2] = 1;
                    setAlarm(3, repeatAlarm);
                    mTuesday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[2] == 1) {
                    isCheckedDay[2] = 0;
                    cancelAlarm(3);
                    mTuesday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[3] == 0) {
                    isCheckedDay[3] = 1;
                    setAlarm(4, repeatAlarm);
                    mWednesday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[3] == 1) {
                    isCheckedDay[3] = 0;
                    cancelAlarm(4);
                    mWednesday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[4] == 0) {
                    isCheckedDay[4] = 1;
                    setAlarm(5, repeatAlarm);
                    mThursday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[4] == 1) {
                    isCheckedDay[4] = 0;
                    cancelAlarm(5);
                    mThursday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[5] == 0) {
                    isCheckedDay[5] = 1;
                    setAlarm(6, repeatAlarm);
                    mFriday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[5] == 1) {
                    isCheckedDay[5] = 0;
                    cancelAlarm(6);
                    mFriday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[6] == 0) {
                    isCheckedDay[6] = 1;
                    setAlarm(7, repeatAlarm);
                    mSaturday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[6] == 1) {
                    isCheckedDay[6] = 0;
                    cancelAlarm(7);
                    mSaturday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mDownpour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.downpour_sound);
                mediaPlayer.start();
                flagOfTune = 0;
                mTuneDetail.setText("Rain and thunderclaps as the alarm tune.");
                mTuneDetail.setVisibility(View.VISIBLE);
                mSetAlarm.setVisibility(View.VISIBLE);
            }
        });

        mKnockerUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.knockerup_sound);
                mediaPlayer.start();
                flagOfTune = 1;
                mTuneDetail.setText("During the 1920s, a knocker-up would knock on the client's door to wake him up.");
                mTuneDetail.setVisibility(View.VISIBLE);
                mSetAlarm.setVisibility(View.VISIBLE);
            }
        });

        mCock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.cock_sound);
                mediaPlayer.start();
                flagOfTune = 2;
                mTuneDetail.setText("Cocks have been used since time unknown as (pretty unreliable) alarm clocks.");
                mTuneDetail.setVisibility(View.VISIBLE);
                mSetAlarm.setVisibility(View.VISIBLE);
            }
        });

        mBonsho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.singing_bowl_sound);
                mediaPlayer.start();
                flagOfTune = 3;
                mTuneDetail.setText("Singing bowls are used in some Buddhist religious practices as well as for meditation and relaxation.");
                mTuneDetail.setVisibility(View.VISIBLE);
                mSetAlarm.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setDayNightTheme() {
        ImageView sunOrMoon = findViewById(R.id.top_image);
        mainActivityLayout = findViewById(R.id.main_activity_layout);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        //change to dusk or dawn theme
        if ((hour >= 4 && hour < 6) || (hour >= 18 && hour < 20)) {
            sunOrMoon.setImageResource(R.drawable.fireflies);
            //          sunOrMoon.setForegroundGravity(CENTER);
            mainActivityLayout.setBackgroundResource(R.drawable.gradient_dawn_dusk);
        }

        //change to night theme
        else if (hour < 6 || hour >= 20) {
            sunOrMoon.setImageResource(R.drawable.moon);
            mainActivityLayout.setBackgroundResource(R.drawable.gradient_night);
        }

        //change to day theme
        else {
            sunOrMoon.setImageResource(R.drawable.sun);
            mainActivityLayout.setBackgroundResource(R.drawable.gradient_day);
        }
    }

    public void setAlarm(int weekNumber, boolean repeat) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_WEEK, weekNumber);
        cal.set(Calendar.HOUR_OF_DAY, userHour);
        cal.set(Calendar.MINUTE, userMinute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, weekNumber, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);

        dateInMillis = cal.getTimeInMillis();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    public void cancelAlarm(int weekNumber) {
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, weekNumber, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(ALARM_KEY, alarmSet);
        preferencesEditor.putBoolean(INCREASING_KEY, increasingVolume);
        preferencesEditor.apply();
    }
}