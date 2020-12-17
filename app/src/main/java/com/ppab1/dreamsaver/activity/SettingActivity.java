package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.helper.TimeHelper;
import com.ppab1.dreamsaver.helper.TimePickerFragment;
import com.ppab1.dreamsaver.preference.UserPreference;
import com.ppab1.dreamsaver.reminder.DailyReminder;
import com.ppab1.dreamsaver.testing.TestingActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.ppab1.dreamsaver.reminder.ReminderHelper.REQUEST_DAILY_REMINDER;
import static com.ppab1.dreamsaver.reminder.ReminderHelper.cancelReminder;
import static com.ppab1.dreamsaver.utils.AppUtils.showToast;
import static com.ppab1.dreamsaver.utils.DateUtils.TIME_FORMAT;
import static com.ppab1.dreamsaver.utils.DateUtils.getArrayTime;

public class SettingActivity extends AppCompatActivity implements TimePickerFragment.DialogTimeListener {
    private final String TIME_PICKER_TAG = "time_picker";
    private TextView tvReminder;
    private UserPreference userPreference;
    private CheckBox cbReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView tvTitle = findViewById(R.id.tv_title_title);
        tvTitle.setText("Setelan");

        ImageButton ibBack = findViewById(R.id.ib_back_title);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CheckBox cbReminder = findViewById(R.id.cb_reminder_setting);
        LinearLayout btnReminder = findViewById(R.id.btn_reminder_setting);
        tvReminder = findViewById(R.id.tv_reminder_setting);

        userPreference = new UserPreference(this);
        cbReminder.setChecked(userPreference.isEnableReminder());
        tvReminder.setText(userPreference.getReminder());

        cbReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                userPreference.setIsEnableReminder(isChecked);
                userPreference.setReminder(tvReminder.getText().toString());

                if (isChecked){
                    new DailyReminder().setDailyReminder(SettingActivity.this, userPreference.getReminder(), "Jangan Lupa Menabung", "Perbarui tabunganmu hari ini.");
                    showToast(SettingActivity.this, "Pengaturan reminder dihidupkan pukul " + userPreference.getReminder());
                } else {
                    cancelReminder(SettingActivity.this, REQUEST_DAILY_REMINDER);
                    showToast(SettingActivity.this, "Pengaturan reminder dimatikan");
                }
            }
        });

        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] arrayTime = getArrayTime(tvReminder.getText().toString());
                TimePickerFragment timePickerFragment = new TimePickerFragment(new TimeHelper(arrayTime[0], arrayTime[1]));
                timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_TAG);
            }
        });

    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        userPreference.setReminder(simpleDateFormat.format(calendar.getTime()));
        tvReminder.setText(userPreference.getReminder());

        if (cbReminder.isChecked()){
            new DailyReminder().setDailyReminder(SettingActivity.this, userPreference.getReminder(),"Jangan Lupa Menabung", "Perbarui tabunganmu hari ini.");
        }
    }
}