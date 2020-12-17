package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import com.ppab1.dreamsaver.helper.DateHelper;
import com.ppab1.dreamsaver.helper.DatePickerFragment;
import com.ppab1.dreamsaver.preference.FirstTimeLaunchPreference;
import com.ppab1.dreamsaver.reminder.DailyReminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.ppab1.dreamsaver.utils.DateUtils.DATE_FORMAT;
import static com.ppab1.dreamsaver.utils.DateUtils.getArrayDate;
import static com.ppab1.dreamsaver.utils.DateUtils.getFullDate;
import static com.ppab1.dreamsaver.utils.EditTextUtils.getFixText;
import static com.ppab1.dreamsaver.utils.EditTextUtils.isNull;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener {
    private EditText edtName, edtDailyTarget, edtSavingsTarget, edtDateTarget;

    private String dateTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        edtName = findViewById(R.id.edt_name_au);
        edtDailyTarget = findViewById(R.id.edt_daily_target_au);
        edtSavingsTarget = findViewById(R.id.edt_savings_target_au);
        edtDateTarget = findViewById(R.id.edt_date_target_au);
        edtDateTarget.setOnClickListener(this);

        Button btnSave = findViewById(R.id.btn_save_au);
        Button btnDelete = findViewById(R.id.btn_delete_au);
        btnSave.setOnClickListener(this);
        btnDelete.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_au:
                String name = getFixText(edtName);
                String dailyTarget = getFixText(edtDailyTarget);
                String savingsTarget = getFixText(edtSavingsTarget);

                // Validasi
                if (isNull(name) || isNull(dailyTarget) || isNull(savingsTarget) || isNull(dateTarget)){
                    if (isNull(name)) edtName.setError("Anda belum mengisi nama target");
                    if (isNull(dailyTarget)) edtDailyTarget.setError("Anda belum mengisi target harian");
                    if (isNull(savingsTarget)) edtSavingsTarget.setError("Anda belum mengisi target menabung");
                    if (isNull(dateTarget)) edtDateTarget.setError("Anda belum memilih tanggal target selesai");
                    return;
                }

                // Simpan
                ContentValues contentValues = new ContentValues();
                contentValues.put(TargetColumns.NAME, name);
                contentValues.put(TargetColumns.DAILY_TARGET, dailyTarget);
                contentValues.put(TargetColumns.SAVINGS_TARGET, savingsTarget);
                contentValues.put(TargetColumns.DATE_TARGET, dateTarget);
                contentValues.put(TargetColumns.TOTAL_SAVINGS, 0);

                getContentResolver().insert(TargetColumns.CONTENT_URI, contentValues);

                FirstTimeLaunchPreference firstTimeLaunchPreference = new FirstTimeLaunchPreference(this);
                firstTimeLaunchPreference.setIsFirstTimeLaunch(false);

                // Atur reminder secara default
                new DailyReminder().setDailyReminder(this,"12:00","Jangan Lupa Menabung", "Perbarui tabunganmu hari ini.");

                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.edt_date_target_au:
                final String DATE_PICKER_TAG = "date_picker";
                DatePickerFragment datePickerFragment;
                if (dateTarget == null) datePickerFragment = new DatePickerFragment();
                else {
                    int[] arrayDate = getArrayDate(dateTarget);
                    datePickerFragment = new DatePickerFragment(new DateHelper(arrayDate[0], arrayDate[1], arrayDate[2]));
                }
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
        }
    }

    @Override
    public void onDialogDataSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        dateTarget = simpleDateFormat.format(calendar.getTime());
        edtDateTarget.setText(getFullDate(dateTarget, false));
    }
}