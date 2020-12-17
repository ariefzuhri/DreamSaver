package com.ppab1.dreamsaver.helper;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private TimeHelper timeHelper;
    DialogTimeListener listener;

    public TimePickerFragment(){}

    public TimePickerFragment(TimeHelper timeHelper){
        this.timeHelper = timeHelper;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        listener = (DialogTimeListener) context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        if (timeHelper == null){
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            timeHelper = new TimeHelper(hour, minute);
        }

        boolean formatHour24 = true;
        return new TimePickerDialog(getActivity(), this, timeHelper.getHour(), timeHelper.getMinute(), formatHour24);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listener.onDialogTimeSet(getTag(), hourOfDay, minute);
    }

    public interface DialogTimeListener{
        void onDialogTimeSet(String tag, int hourOfDay, int minute);
    }

}
