package com.ninestarstudios.breeze;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String userName, alarmTime;
    String sharedPrefFile = "com.ninestarstudios.breeze";
    LinearLayout mTunes1, mRepeatDays1, mRepeatDays2, mTunes2;
    ConstraintLayout mainActivityLayout;
    private static final String TAG = "MainActivity";
    boolean alarmSet, increasingVolume;
    private final String ALARM_KEY = "alarm";
    private final String WELCOME_SCREEN = "welcome";
    private final String USER_NAME = "name";
    private final String ALARM_TIME = "alarm_time";
    private final String REPEATING = "repeating";
    private final String VIBRATING = "vibrating";
    boolean vibrate;
    final String FLAG_OF_TUNE = "flag";
    SharedPreferences mPreferences;
    TextView mTuneDetail, greetings, mSetAlarm, mCancel, mNext, mDownpour, mKnockerUp, mCock, mBonsho, mSettings;
    MediaPlayer mediaPlayer;
    int[] isCheckedDay = {0, 0, 0, 0, 0, 0, 0};
    Button mSunday, mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday;
    CheckBox repeatAlarmCheckBox;
    int userHour = 0, userMinute = 0;
    boolean repeatAlarm = false;
    int flagOfTune, welcomeScreen;
    TimePicker alarmTimePicker;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityLayout = findViewById(R.id.main_activity_layout);
        mRepeatDays1 = findViewById(R.id.repeat_days_1);
        mRepeatDays2 = findViewById(R.id.repeat_days_2);
        mTunes1 = findViewById(R.id.tunes1);
        mTunes2 = findViewById(R.id.tunes2);
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
        mBonsho = findViewById(R.id.singing_bowl);
        repeatAlarmCheckBox = findViewById(R.id.repeat_alarm);
        mSetAlarm = findViewById(R.id.set_alarm);
        greetings = findViewById(R.id.greetings);
        alarmTimePicker = findViewById(R.id.alarm_time_picker);
        mCancel = findViewById(R.id.cancel);
        mNext = findViewById(R.id.next);
        mSettings = findViewById(R.id.settings);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        welcomeScreen = mPreferences.getInt(WELCOME_SCREEN, 0);
        if (welcomeScreen == 0) {
            welcomeScreen = 1;
            preferencesEditor.putInt(WELCOME_SCREEN, welcomeScreen);
            preferencesEditor.apply();
            Intent welcomeActivityIntent = new Intent(this, WelcomeActivity.class);
            startActivity(welcomeActivityIntent);
        }

        userName = mPreferences.getString(USER_NAME, "");
        preferencesEditor.putString(USER_NAME, userName);
        preferencesEditor.apply();
        setGreeting();

        alarmSet = mPreferences.getBoolean(ALARM_KEY, false);
        if (alarmSet) {
            mSetAlarm.setText(mPreferences.getString(ALARM_TIME, "Set Alarm"));
        }

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSettingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(startSettingsActivity);
            }
        });

        i = 0;

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetAlarm.setVisibility(View.VISIBLE);
                alarmTimePicker.setVisibility(View.GONE);
                repeatAlarmCheckBox.setVisibility(View.GONE);
                mRepeatDays1.setVisibility(View.GONE);
                mRepeatDays2.setVisibility(View.GONE);
                mTunes1.setVisibility(View.GONE);
                mTunes2.setVisibility(View.GONE);
                mTuneDetail.setVisibility(View.GONE);
                mCancel.setVisibility(View.GONE);
                mNext.setVisibility(View.GONE);
                i = 0;
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    userHour = alarmTimePicker.getHour();
                    userMinute = alarmTimePicker.getMinute();
                    Log.d(TAG, Integer.toString(userHour));
                    alarmTimePicker.setVisibility(View.GONE);
                    repeatAlarmCheckBox.setAlpha(0.0f);
                    repeatAlarmCheckBox.setVisibility(View.VISIBLE);
                    repeatAlarmCheckBox.animate().alpha(1.0f).setDuration(500);
                    i = 2;
                } else if (i == 1) {
                    repeatAlarmCheckBox.setVisibility(View.GONE);
                    /*mRepeatDays1.setAlpha(0.0f);
                    mRepeatDays2.setAlpha(0.0f);
                    mRepeatDays1.setVisibility(View.VISIBLE);
                    mRepeatDays2.setVisibility(View.VISIBLE);
                    mRepeatDays2.animate().alpha(1.0f).setDuration(500);
                    mRepeatDays1.animate().alpha(1.0f).setDuration(500);*/
                    i = 2;
                } else if (i == 2) {
                    repeatAlarmCheckBox.setVisibility(View.GONE);
                    mRepeatDays1.setVisibility(View.GONE);
                    mRepeatDays2.setVisibility(View.GONE);
                    mTunes1.setAlpha(0.0f);
                    mTunes1.setVisibility(View.VISIBLE);
                    mTunes1.animate().alpha(1.0f).setDuration(500);
                    mTunes2.setAlpha(0.0f);
                    mTunes2.setVisibility(View.VISIBLE);
                    mTunes2.animate().alpha(1.0f).setDuration(500);
                    i = 3;
                } else {
                    if (mediaPlayer == null) {
                        Toast.makeText(MainActivity.this, "Please choose an alarm tune", Toast.LENGTH_LONG).show();
                    } else {
                        mTunes1.setVisibility(View.GONE);
                        mTunes2.setVisibility(View.GONE);
                        mTuneDetail.setVisibility(View.GONE);
                        mCancel.setVisibility(View.GONE);
                        mNext.setVisibility(View.GONE);
                        setAlarm(repeatAlarm);
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        //TODO: Change setalarm textview to show alarm timing and repeat frequency
                    }
                }
            }
        });

        repeatAlarmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                repeatAlarm = isChecked;
                onPause();
            }
        });

        mSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSetAlarm.getText().toString().equalsIgnoreCase("Set Alarm")) {
                    Log.d(TAG, "Here's your name:" + userName);
                    //AlarmDialog alarmDialog = new AlarmDialog();
                    //alarmDialog.show(getSupportFragmentManager(), "AlarmDialog");
                    mSetAlarm.setVisibility(View.GONE);
                    alarmTimePicker.setAlpha(0.0f);
                    alarmTimePicker.setVisibility(View.VISIBLE);
                    alarmTimePicker.animate().alpha(1.0f).setDuration(500);
                    mNext.setVisibility(View.VISIBLE);
                    mCancel.setVisibility(View.VISIBLE);
                } else {
                    cancelAlarm();
                    Toast.makeText(MainActivity.this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
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
                preferencesEditor.apply();
                mTuneDetail.setText("Rain and thunderclaps as the alarm tune.");
                mTuneDetail.setVisibility(View.VISIBLE);
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
                preferencesEditor.apply();
                mTuneDetail.setText("During the 1920s, a knocker-up would knock on the client's door to wake him up.");
                mTuneDetail.setVisibility(View.VISIBLE);
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
                preferencesEditor.apply();
                mTuneDetail.setText("Cocks have been used since time unknown as (pretty unreliable) alarm clocks.");
                mTuneDetail.setVisibility(View.VISIBLE);
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
                preferencesEditor.apply();
                mTuneDetail.setText("Singing bowls are used in some Buddhist religious practices as well as for meditation and relaxation.");
                mTuneDetail.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setAlarm(boolean repeatAlarm) {

        Log.d(TAG, mPreferences.getString("name", ""));
        Calendar cal = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();

        cal.set(Calendar.HOUR_OF_DAY, userHour);
        cal.set(Calendar.MINUTE, userMinute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int toastHour = userHour;
        String meridian = "am";
        if (toastHour == 12 && userMinute > 0) {
            meridian = " pm";
        } else if (toastHour > 13) {
            toastHour -= 12;
            meridian = " pm";
        }
        String toastMinute = Integer.toString(userMinute);
        if (userMinute < 10)
            toastMinute = "0" + toastMinute;
        String ringingFrequency = "";

        if (cal.getTimeInMillis() < currentTime && !repeatAlarm) {
            cal.setTimeInMillis(cal.getTimeInMillis() + (24 * 60 * 60 * 1000));
            ringingFrequency = " tomorrow" + ringingFrequency;
        } else if (cal.getTimeInMillis() < currentTime && repeatAlarm) {
            cal.setTimeInMillis(cal.getTimeInMillis() + (24 * 60 * 60 * 1000));
            ringingFrequency = " everyday" + ringingFrequency;
        } else if (repeatAlarm)
            ringingFrequency = " everyday" + ringingFrequency;

        if (!repeatAlarm) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        }
        Toast.makeText(MainActivity.this, "Alarm set for " + toastHour + ":" + toastMinute + " " + meridian + ringingFrequency, Toast.LENGTH_LONG).show();
        mSetAlarm.setVisibility(View.VISIBLE);
        alarmTime = toastHour + ":" + toastMinute + meridian + ringingFrequency;
        alarmSet = true;
        onPause();
        mSetAlarm.setText(alarmTime);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(MainActivity.this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        mSetAlarm.setText("Set Alarm");

    }

    public void setGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (userName.length() == 0) {
            if (hour >= 4 && hour < 12)
                greetings.setText("Good morning!");
            else if (hour >= 12 && hour < 17)
                greetings.setText("Having a nice day?");
            else if (hour >= 17 && hour < 22)
                greetings.setText("'tis play time!");
            else
                greetings.setText("Sleep well!");
        } else {
            if (hour >= 4 && hour < 12)
                greetings.setText("Good morning, " + userName + "!");
            else if (hour >= 12 && hour < 17)
                greetings.setText("Having a nice day " + userName + "?");
            else if (hour >= 17 && hour < 22)
                greetings.setText("'tis play time, " + userName + " :)");
            else
                greetings.setText("Sleep well " + userName + ".");
        }
        greetings.setAlpha(1.0f);
        greetings.setVisibility(View.VISIBLE);
        greetings.animate().alpha(0.0f).setDuration(4000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(ALARM_KEY, alarmSet);
        //String INCREASING_KEY = "increasing";
        //preferencesEditor.putBoolean(INCREASING_KEY, increasingVolume);
        preferencesEditor.putInt(WELCOME_SCREEN, welcomeScreen);
        preferencesEditor.putString(USER_NAME, userName);
        preferencesEditor.putInt(FLAG_OF_TUNE, flagOfTune);
        preferencesEditor.putString(ALARM_TIME, alarmTime);
        preferencesEditor.putBoolean(REPEATING, repeatAlarm);
        //preferencesEditor.putBoolean(VIBRATING, vibrate);
        preferencesEditor.apply();
    }
}

 /* repeatAlarmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });*/