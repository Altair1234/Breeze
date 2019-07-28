package com.ninestarstudios.breeze;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

public class AlarmDialog extends DialogFragment {

    Button mConfirm, mCancel;
    TimePicker mAlarmTimePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_alarm, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlarmTimePicker = view.findViewById(R.id.alarm_time_picker);
        mConfirm = view.findViewById(R.id.confirm_time);
        mCancel = view.findViewById(R.id.cancel);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                MainActivity.repeatAlarmCheckBox.setVisibility(View.VISIBLE);
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.userHour = mAlarmTimePicker.getHour();
                MainActivity.userMinute = mAlarmTimePicker.getMinute();
                MainActivity.repeatAlarmCheckBox.setVisibility(View.VISIBLE);
                getDialog().dismiss();
            }
        });
        return view;
    }
}
