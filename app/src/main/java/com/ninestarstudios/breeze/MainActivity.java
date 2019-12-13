package com.ninestarstudios.breeze;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String mUserName, mAlarmTime;
    String sharedPrefFile = "com.ninestarstudios.breeze";
    ConstraintLayout mainActivityLayout;
    ImageView mainBackground;
    boolean mAlarmSet;
    private final String ALARM_KEY = "alarm";
    private final String WELCOME_SCREEN = "welcome";
    private final String USER_NAME = "name";
    private final String ALARM_TIME = "alarm_time";
    private final String REPEATING = "repeating";
    final String BACKGROUND = "background";
    final String FLAG_OF_TUNE = "flag";
    SharedPreferences preferences;
    TextView tuneDetail, greetings, cancel, next, downpour, knockerUp, cock, singingBowl, chooseTune;
    MediaPlayer mediaPlayer;
    Button setAlarm, settings;
    CheckBox repeatAlarmCheckBox;
    int mUserHour = 0, mUserMinute = 0, mScreenCounter, mFlagOfTune, mWelcomeScreen, mBackground;
    boolean mRepeatAlarm = false;
    TimePicker alarmTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityLayout = findViewById(R.id.main_activity_layout);
        chooseTune = findViewById(R.id.choose_tune);
        tuneDetail = findViewById(R.id.tune_details);
        downpour = findViewById(R.id.downpour);
        knockerUp = findViewById(R.id.knockerup);
        cock = findViewById(R.id.cock);
        singingBowl = findViewById(R.id.singing_bowl);
        repeatAlarmCheckBox = findViewById(R.id.repeat_alarm);
        setAlarm = findViewById(R.id.set_alarm);
        greetings = findViewById(R.id.greetings);
        alarmTimePicker = findViewById(R.id.alarm_time_picker);
        cancel = findViewById(R.id.cancel);
        next = findViewById(R.id.next);
        settings = findViewById(R.id.settings);
        mainBackground = findViewById(R.id.main_background);

        preferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = preferences.edit();

        mWelcomeScreen = preferences.getInt(WELCOME_SCREEN, 0);
        if (mWelcomeScreen == 0) {
            mWelcomeScreen = 1;
            preferencesEditor.putInt(WELCOME_SCREEN, mWelcomeScreen);
            preferencesEditor.apply();
            Intent welcomeActivityIntent = new Intent(this, WelcomeActivity.class);
            startActivity(welcomeActivityIntent);
        }

        mBackground = preferences.getInt(BACKGROUND, R.drawable.background_1);
        if (mBackground != R.drawable.background_1)
            mainBackground.setImageResource(mBackground);

        mUserName = preferences.getString(USER_NAME, "");
        preferencesEditor.putString(USER_NAME, mUserName);
        preferencesEditor.apply();
        setGreeting();

        mAlarmSet = preferences.getBoolean(ALARM_KEY, false);
        setAlarm.setText(preferences.getString(ALARM_TIME, "Set Alarm"));

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSettingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(startSettingsActivity);
            }
        });

        mScreenCounter = 0;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm.setVisibility(View.VISIBLE);
                alarmTimePicker.setVisibility(View.GONE);
                repeatAlarmCheckBox.setVisibility(View.GONE);
                chooseTune.setVisibility(View.GONE);
                cock.setVisibility(View.GONE);
                downpour.setVisibility(View.GONE);
                knockerUp.setVisibility(View.GONE);
                singingBowl.setVisibility(View.GONE);
                tuneDetail.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                mScreenCounter = 0;
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mScreenCounter == 0) {
                    mUserHour = alarmTimePicker.getHour();
                    mUserMinute = alarmTimePicker.getMinute();
                    alarmTimePicker.setVisibility(View.GONE);
                    repeatAlarmCheckBox.setAlpha(0.0f);
                    repeatAlarmCheckBox.setVisibility(View.VISIBLE);
                    repeatAlarmCheckBox.animate().alpha(1.0f).setDuration(500);
                    mScreenCounter = 1;
                } else if (mScreenCounter == 1) {
                    repeatAlarmCheckBox.setVisibility(View.GONE);
                    chooseTune.setAlpha(0.0f);
                    chooseTune.setVisibility(View.VISIBLE);
                    chooseTune.animate().alpha(1.0f).setDuration(500);
                    downpour.setAlpha(0.0f);
                    downpour.setVisibility(View.VISIBLE);
                    downpour.animate().alpha(1.0f).setDuration(500);
                    knockerUp.setAlpha(0.0f);
                    knockerUp.setVisibility(View.VISIBLE);
                    knockerUp.animate().alpha(1.0f).setDuration(500);
                    cock.setAlpha(0.0f);
                    cock.setVisibility(View.VISIBLE);
                    cock.animate().alpha(1.0f).setDuration(500);
                    singingBowl.setAlpha(0.0f);
                    singingBowl.setVisibility(View.VISIBLE);
                    singingBowl.animate().alpha(1.0f).setDuration(500);
                    mScreenCounter = 2;
                } else {
                    if (mediaPlayer == null) {
                        Toast.makeText(MainActivity.this, "Please choose an alarm tune", Toast.LENGTH_LONG).show();
                    } else {
                        chooseTune.setVisibility(View.GONE);
                        cock.setVisibility(View.GONE);
                        downpour.setVisibility(View.GONE);
                        knockerUp.setVisibility(View.GONE);
                        singingBowl.setVisibility(View.GONE);
                        tuneDetail.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        scheduleAlarm(mRepeatAlarm);
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    }
                }
            }
        });

        repeatAlarmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRepeatAlarm = isChecked;
                onPause();
            }
        });

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setAlarm.getText().toString().equalsIgnoreCase("Set Alarm")) {
                    setAlarm.setVisibility(View.GONE);
                    alarmTimePicker.setAlpha(0.0f);
                    alarmTimePicker.setVisibility(View.VISIBLE);
                    alarmTimePicker.animate().alpha(1.0f).setDuration(500);
                    next.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                } else {
                    cancelAlarm();
                    Toast.makeText(MainActivity.this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        downpour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.downpour_sound);
                mediaPlayer.start();
                mFlagOfTune = 0;
                preferencesEditor.apply();
                tuneDetail.setText("Rain and thunderclaps as the alarm tune.");
                tuneDetail.setVisibility(View.VISIBLE);
            }
        });

        knockerUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.knockerup_sound);
                mediaPlayer.start();
                mFlagOfTune = 1;
                preferencesEditor.apply();
                tuneDetail.setText("During the 1920s, a knocker-up would knock on the client's door to wake him up.");
                tuneDetail.setVisibility(View.VISIBLE);
            }
        });

        cock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.cock_sound);
                mediaPlayer.start();
                mFlagOfTune = 2;
                preferencesEditor.apply();
                tuneDetail.setText("Cocks have been used since time unknown as (pretty unreliable) alarm clocks.");
                tuneDetail.setVisibility(View.VISIBLE);
            }
        });

        singingBowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.singing_bowl_sound);
                mediaPlayer.start();
                mFlagOfTune = 3;
                preferencesEditor.apply();
                tuneDetail.setText("Singing bowls are used in some Buddhist religious practices as well as for meditation and relaxation.");
                tuneDetail.setVisibility(View.VISIBLE);
            }
        });

    }

    public void scheduleAlarm(boolean repeatAlarm) {

        Calendar cal = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();

        cal.set(Calendar.HOUR_OF_DAY, mUserHour);
        cal.set(Calendar.MINUTE, mUserMinute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int toastHour = mUserHour;
        String meridian = "am";
        if (toastHour == 12 && mUserMinute > 0) {
            meridian = " pm";
        } else if (toastHour >= 13) {
            toastHour -= 12;
            meridian = " pm";
        }
        String toastMinute = Integer.toString(mUserMinute);
        if (mUserMinute < 10)
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
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        }
        Toast.makeText(MainActivity.this, "Alarm set for " + toastHour + ":" + toastMinute + " " + meridian + ringingFrequency, Toast.LENGTH_LONG).show();
        greetings.setVisibility(View.VISIBLE);
        mAlarmTime = toastHour + ":" + toastMinute + meridian + ringingFrequency;
        mAlarmSet = true;
        onPause();
        setAlarm.setText("Cancel");
        setAlarm.setVisibility(View.VISIBLE);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(MainActivity.this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        setAlarm.setText("Set Alarm");
        greetings.setText("");
        greetings.setVisibility(View.GONE);
    }

    public void setGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (mUserName.length() == 0) {
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
                greetings.setText("Good morning, " + mUserName + "!");
            else if (hour >= 12 && hour < 17)
                greetings.setText("Having a nice day " + mUserName + "?");
            else if (hour >= 17 && hour < 22)
                greetings.setText("'tis play time, " + mUserName + " :)");
            else
                greetings.setText("Sleep well " + mUserName + ".");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putBoolean(ALARM_KEY, mAlarmSet);
        preferencesEditor.putInt(WELCOME_SCREEN, mWelcomeScreen);
        preferencesEditor.putString(USER_NAME, mUserName);
        preferencesEditor.putInt(FLAG_OF_TUNE, mFlagOfTune);
        preferencesEditor.putString(ALARM_TIME, mAlarmTime);
        preferencesEditor.putBoolean(REPEATING, mRepeatAlarm);
        preferencesEditor.putInt(BACKGROUND, mBackground);
        preferencesEditor.apply();
    }
}
/*LinearLayout mRepeatDays1, mRepeatDays2;
Button sunday, mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday,
mRepeatDays1 = findViewById(R.id.repeat_days_1);
mRepeatDays2 = findViewById(R.id.repeat_days_2);
int[] isCheckedDay = {0, 0, 0, 0, 0, 0, 0};
mRepeatDays1.setVisibility(View.GONE);
mRepeatDays2.setVisibility(View.GONE);
 sunday = findViewById(R.id.sunday);
                    mRepeatDays1.setVisibility(View.GONE);
                    mRepeatDays2.setVisibility(View.GONE);
        mMonday = findViewById(R.id.monday);
        mTuesday = findViewById(R.id.tuesday);
        mWednesday = findViewById(R.id.wednesday);
        mThursday = findViewById(R.id.thursday);
        mFriday = findViewById(R.id.friday);
        mSaturday = findViewById(R.id.saturday);
mRepeatDays1.setAlpha(0.0f);
                    mRepeatDays2.setAlpha(0.0f);
                    mRepeatDays1.setVisibility(View.VISIBLE);
                    mRepeatDays2.setVisibility(View.VISIBLE);
                    mRepeatDays2.animate().alpha(1.0f).setDuration(500);
                    mRepeatDays1.animate().alpha(1.0f).setDuration(500);

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

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[0] == 0) {
                    isCheckedDay[0] = 1;
                    setAlarm(1, mRepeatAlarm);
                    sunday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[0] == 1) {
                    isCheckedDay[0] = 0;
                    cancelAlarm(1);
                    sunday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });

        mMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDay[1] == 0) {
                    isCheckedDay[1] = 1;
                    setAlarm(2, mRepeatAlarm);
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
                    setAlarm(3, mRepeatAlarm);
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
                    setAlarm(4, mRepeatAlarm);
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
                    setAlarm(5, mRepeatAlarm);
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
                    setAlarm(6, mRepeatAlarm);
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
                    setAlarm(7, mRepeatAlarm);
                    mSaturday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_selected));
                } else if (isCheckedDay[6] == 1) {
                    isCheckedDay[6] = 0;
                    cancelAlarm(7);
                    mSaturday.setBackground(AppCompatResources.getDrawable(MainActivity.this, R.drawable.round_button_unselected));
                }
            }
        });*/