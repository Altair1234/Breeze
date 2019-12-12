package com.ninestarstudios.breeze;

import android.content.SharedPreferences;
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
    SharedPreferences preferences;
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

        preferences = getSharedPreferences("com.ninestarstudios.breeze", MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = preferences.edit();

        mIncreasingVolume = preferences.getBoolean("increasing", false);
        setIncreasing.setChecked(mIncreasingVolume);

        mVibrate = preferences.getBoolean("vibrating", false);
        setVibrating.setChecked(mVibrate);
        mBackground = preferences.getInt("background", R.drawable.background_1);
        if (mBackground != R.drawable.background_1)
            settingsBackground.setImageResource(mBackground);

        String name = preferences.getString("name", "");
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
                    preferencesEditor.putString("name", newName);
                    preferencesEditor.apply();
                    changeNameLL.setVisibility(View.GONE);
                }
            }
        });

        setIncreasing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingsActivity.this, "Volume of alarm will increase until stopped", Toast.LENGTH_LONG).show();
                    mIncreasingVolume = true;
                } else {
                    mIncreasingVolume = false;
                }
                preferencesEditor.putBoolean("increasing", mIncreasingVolume);
                preferencesEditor.apply();
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
                preferencesEditor.putBoolean("vibrating", mVibrate);
                preferencesEditor.apply();
            }
        });

        changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setAlpha(0.0f);
                option1.setVisibility(View.VISIBLE);
                option1.animate().alpha(1.0f).setDuration(500);
                option2.setAlpha(0.0f);
                option2.setVisibility(View.VISIBLE);
                option2.animate().alpha(1.0f).setDuration(500);
                option3.setAlpha(0.0f);
                option3.setVisibility(View.VISIBLE);
                option3.animate().alpha(1.0f).setDuration(500);
                option4.setAlpha(0.0f);
                option4.setVisibility(View.VISIBLE);
                option4.animate().alpha(1.0f).setDuration(500);
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackground = R.drawable.background_1;
                preferencesEditor.apply();
                MainActivity.mainBackground.setImageResource(R.drawable.background_1);
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
                preferencesEditor.apply();
                MainActivity.mainBackground.setImageResource(R.drawable.background_2);
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
                preferencesEditor.apply();
                MainActivity.mainBackground.setImageResource(R.drawable.background_3);
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
                preferencesEditor.apply();
                MainActivity.mainBackground.setImageResource(R.drawable.background_4);
                settingsBackground.setImageResource(R.drawable.background_4);
                option1.setVisibility(View.GONE);
                option2.setVisibility(View.GONE);
                option3.setVisibility(View.GONE);
                option4.setVisibility(View.GONE);            }
        });
    }
}
