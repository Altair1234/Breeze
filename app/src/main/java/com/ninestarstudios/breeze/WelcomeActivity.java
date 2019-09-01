package com.ninestarstudios.breeze;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    EditText userName;
    TextView skip, nameEnter;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPreferences = getSharedPreferences("com.ninestarstudios.breeze", MODE_PRIVATE);
        final SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        skip = findViewById(R.id.skip_or_enter);
        userName = findViewById(R.id.username_edittext);
        nameEnter = findViewById(R.id.name_enter);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivity = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
            }
        });

        nameEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().length() != 0) {
                    preferencesEditor.putString("name", userName.getText().toString());
                    preferencesEditor.apply();
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(WelcomeActivity.this, "Please enter your name or skip!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
