package com.ninestarstudios.breeze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Switch setIncreasing, setVibrating;
    TextView changeNameTV, changeNameDone, changeBackground;
    LinearLayout changeNameLL;
    EditText changeNameET;
    ImageView option1, option2, option3, option4, settingsBackground;
    int mBackground;
    boolean mVibrate, mIncreasingVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setIncreasing = findViewById(R.id.increasing_volume);
        changeNameDone = findViewById(R.id.change_name_done);
        changeNameET = findViewById(R.id.change_name_et);
        changeNameTV = findViewById(R.id.change_name_tv);
        changeNameLL = findViewById(R.id.change_name_ll);
        setVibrating = findViewById(R.id.vibrating);
        settingsBackground = findViewById(R.id.settings_background);
        changeBackground = findViewById(R.id.change_background);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);

        SharedPref.init(getApplicationContext());

        mIncreasingVolume = SharedPref.read(SharedPref.INCREASING, false);
        setIncreasing.setChecked(mIncreasingVolume);

        mVibrate = SharedPref.read(SharedPref.VIBRATING, false);
        setVibrating.setChecked(mVibrate);
        mBackground = SharedPref.read(SharedPref.BACKGROUND, R.drawable.background_1);

        settingsBackground.setImageResource(mBackground);

        String name = SharedPref.read(SharedPref.USER_NAME, "");
        if (name != null && !name.equals(""))
            changeNameET.setText(name);

        changeNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeNameLL.setAlpha(0.0f);
                changeNameLL.setVisibility(View.VISIBLE);
                changeNameLL.animate().alpha(1.0f).setDuration(500);
            }
        });

        changeNameDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeNameET.getText() == null || changeNameET.getText().toString().equals("")) {
                    Toast.makeText(SettingsActivity.this, "No name entered", Toast.LENGTH_LONG).show();
                    changeNameLL.setVisibility(View.GONE);
                } else {
                    String newName = changeNameET.getText().toString();
                    Toast.makeText(SettingsActivity.this, "Welcome, " + newName + "!", Toast.LENGTH_LONG).show();
                    SharedPref.write(SharedPref.USER_NAME, newName);
                    changeNameLL.setVisibility(View.GONE);
                }
            }
        });

        setIncreasing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingsActivity.this, "Volume of alarm will increase until stopped (experimental feature)", Toast.LENGTH_LONG).show();
                    mIncreasingVolume = true;
                } else {
                    mIncreasingVolume = false;
                }
                SharedPref.write(SharedPref.INCREASING, mIncreasingVolume);
            }
        });

        setVibrating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingsActivity.this, "Alarm vibration on", Toast.LENGTH_LONG).show();
                    mVibrate = true;
                } else {
                    mVibrate = false;
                }
                SharedPref.write(SharedPref.VIBRATING, mVibrate);
            }
        });

        changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setVisibility(View.VISIBLE);
                option2.setVisibility(View.VISIBLE);
                option3.setVisibility(View.VISIBLE);
                option4.setVisibility(View.VISIBLE);
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackground = R.drawable.background_1;
                SharedPref.write(SharedPref.BACKGROUND, mBackground);
                settingsBackground.setImageResource(R.drawable.background_1);
                option1.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);
                option3.setVisibility(View.GONE);
                option4.setVisibility(View.GONE);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackground = R.drawable.background_2;
                SharedPref.write(SharedPref.BACKGROUND, mBackground);
                settingsBackground.setImageResource(R.drawable.background_2);
                option1.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);
                option3.setVisibility(View.GONE);
                option4.setVisibility(View.GONE);            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackground = R.drawable.background_3;
                SharedPref.write(SharedPref.BACKGROUND, mBackground);
                settingsBackground.setImageResource(R.drawable.background_3);
                option1.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);
                option3.setVisibility(View.GONE);
                option4.setVisibility(View.GONE);            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackground = R.drawable.background_4;
                SharedPref.write(SharedPref.BACKGROUND, mBackground);
                settingsBackground.setImageResource(R.drawable.background_4);
                option1.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);
                option3.setVisibility(View.GONE);
                option4.setVisibility(View.GONE);            }
        });
    }
}
