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

    Switch mSetIncreasing, mSetVibrating, mSiren;
    TextView mChangeNameTV, mChangeNameDone, mSnoozeConfirmTV;
    LinearLayout mChangeNameLL, mSnoozeConfirmLL;
    EditText mChangeNameET;
    SharedPreferences mPreferences;
    boolean vibrate, increasingVolume, siren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSetIncreasing = findViewById(R.id.increasing_volume);
        mChangeNameDone = findViewById(R.id.change_name_done);
        mChangeNameET = findViewById(R.id.change_name_et);
        mChangeNameTV = findViewById(R.id.change_name_tv);
        mChangeNameLL = findViewById(R.id.change_name_ll);
        mSetVibrating = findViewById(R.id.vibrating);
        mSiren = findViewById(R.id.siren);
        mSnoozeConfirmLL = findViewById(R.id.confirm_snooze_ll);
        mSnoozeConfirmTV = findViewById(R.id.confirm_snooze_tv);

        mPreferences = getSharedPreferences("com.ninestarstudios.breeze", MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        increasingVolume = mPreferences.getBoolean("increasing", true);
        mSetIncreasing.setChecked(increasingVolume);

        vibrate = mPreferences.getBoolean("vibrating", false);
        mSetVibrating.setChecked(vibrate);

        siren = mPreferences.getBoolean("siren", false);
        mSiren.setChecked(siren);

        String name = mPreferences.getString("name", "");
        if (name != null && !name.equals(""))
            mChangeNameET.setText(name);

        mChangeNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangeNameLL.setAlpha(0.0f);
                mChangeNameLL.setVisibility(View.VISIBLE);
                mChangeNameLL.animate().alpha(1.0f).setDuration(500);
            }
        });

        mChangeNameDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChangeNameET.getText() == null || mChangeNameET.getText().toString().equals("")) {
                    Toast.makeText(SettingsActivity.this, "No name entered", Toast.LENGTH_LONG).show();
                    mChangeNameLL.setVisibility(View.GONE);
                } else {
                    String newName = mChangeNameET.getText().toString();
                    Toast.makeText(SettingsActivity.this, "Welcome, " + newName + "!", Toast.LENGTH_LONG).show();
                    preferencesEditor.putString("name", newName);
                    preferencesEditor.apply();
                    mChangeNameLL.setVisibility(View.GONE);
                }
            }
        });

        mSiren.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSnoozeConfirmLL.setAlpha(0.0f);
                    mSnoozeConfirmLL.setVisibility(View.VISIBLE);
                    mSnoozeConfirmLL.animate().alpha(1.0f).setDuration(500);
                } else {
                    mSnoozeConfirmLL.setVisibility(View.GONE);
                    preferencesEditor.putBoolean("siren", false);
                    preferencesEditor.putBoolean("siren_confirm", false);
                    preferencesEditor.apply();
                }
            }
        });

        mSnoozeConfirmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnoozeConfirmLL.setVisibility(View.GONE);
                preferencesEditor.putBoolean("siren", true);
                preferencesEditor.putBoolean("siren_confirm", true);
                preferencesEditor.apply();
                Toast.makeText(SettingsActivity.this, "\"Snooze\" enabled", Toast.LENGTH_LONG).show();
            }
        });

        mSetIncreasing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingsActivity.this, "Volume of alarm will increase until stopped", Toast.LENGTH_LONG).show();
                    increasingVolume = true;
                } else {
                    increasingVolume = false;
                }
                preferencesEditor.putBoolean("increasing", increasingVolume);
                preferencesEditor.apply();
            }
        });

        mSetVibrating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingsActivity.this, "Alarm vibration on", Toast.LENGTH_LONG).show();
                    vibrate = true;
                } else {
                    vibrate = false;
                }
                preferencesEditor.putBoolean("vibrating", vibrate);
                preferencesEditor.apply();
            }
        });
    }
}
