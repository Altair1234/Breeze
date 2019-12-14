package com.ninestarstudios.breeze;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

class SharedPref {
    private static SharedPreferences mSharedPref;
    static final String ALARM_KEY = "alarm";
    static final String WELCOME_SCREEN = "welcome";
    static final String USER_NAME = "name";
    static final String ALARM_TIME = "alarm_time";
    static final String REPEATING = "repeating";
    static final String BACKGROUND = "background";
    static final String FLAG_OF_TUNE = "flag";
    static final String INCREASING = "increasing";
    static final String VIBRATING = "vibrating";
    static final String TIME_IN_MILLIS = "time_in_millis";

    private SharedPref() {
    }

    static void init(Context context){
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    static String read(String key, String defValue){
        return mSharedPref.getString(key, defValue);
    }

    static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }

    static Long read(String key, long defValue) {
        return mSharedPref.getLong(key, defValue);
    }

    static void write(String key, Long value){
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putLong(key, value).commit();
    }
}
