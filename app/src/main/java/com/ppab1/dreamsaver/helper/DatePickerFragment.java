package com.ppab1.dreamsaver.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DateHelper dateHelper;
    DialogDateListener listener;

    public DatePickerFragment(){}

    public DatePickerFragment(DateHelper dateHelper){
        this.dateHelper = dateHelper;
    }

    // Mengaitkan dengan activity pemanggil
    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        listener = (DialogDateListener) context;
    }

    // Melepas kaitan dengan activity pemanggil
    @Override
    public void onDetach(){
        super.onDetach();
        listener = null;
    }

    // Memunculkan dialog memilih tanggal
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        if (dateHelper == null){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int date = calendar.get(Calendar.DATE);
            dateHelper = new DateHelper(year, month, date);
        }

        return new DatePickerDialog(getActivity(), this, dateHelper.getYear(), dateHelper.getMonth(), dateHelper.getDate());
    }

    // Dipanggil ketika sudah memilih tanggal
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        listener.onDialogDataSet(getTag(), year, month, dayOfMonth);
    }

    // Mengirim tanggal yang dipilih ke MainActivity dengan bantuan interface
    public interface DialogDateListener{
        void onDialogDataSet(String tag, int year, int month, int dayOfMonth);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
