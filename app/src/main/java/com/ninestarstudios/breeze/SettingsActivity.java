package com.ninestarstudios.breeze;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Switch setIncreasing, setVibrating;
    TextView changeNameTV, changeNameDone;
    LinearLayout changeNameLL;
    EditText changeNameET;
    SharedPreferences preferences;
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

        preferences = getSharedPreferences("com.ninestarstudios.breeze", MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = preferences.edit();

        mIncreasingVolume = preferences.getBoolean("increasing", false);
        setIncreasing.setChecked(mIncreasingVolume);

        mVibrate = preferences.getBoolean("vibrating", false);
        setVibrating.setChecked(mVibrate);

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
    }
}
