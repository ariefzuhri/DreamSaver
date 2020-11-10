package com.ppab1.dreamsaver.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.MainActivity;
import com.ppab1.dreamsaver.reminder.DailyReminder;

import static com.ppab1.dreamsaver.reminder.ReminderHelper.REQUEST_DAILY_REMINDER;
import static com.ppab1.dreamsaver.reminder.ReminderHelper.cancelReminder;
import static com.ppab1.dreamsaver.reminder.ReminderHelper.sendNotification;
import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.hideStatusBar;
import static com.ppab1.dreamsaver.utils.AppUtils.showToast;

public class TestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        hideStatusBar(this, getSupportActionBar());
        Log.d(getClass().getSimpleName(), "Sisa hari: " + getRemainingDays(10000, 500, 700));

        Button btnStart = findViewById(R.id.btn_next_intro_testing);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestingActivity.this, MainActivity.class));
            }
        });

        String dummyTitle = "Lorem ipsum";
        String dummyMessage = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        Button btnSendNotification = findViewById(R.id.btn_send_notification_testing);
        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification(TestingActivity.this, REQUEST_DAILY_REMINDER, dummyTitle, dummyMessage);
                showToast(TestingActivity.this, "Cek notif bar");
            }
        });

        Button btnEnableReminder = findViewById(R.id.btn_enable_reminder_testing);
        btnEnableReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DailyReminder().setDailyReminder(TestingActivity.this, dummyTitle, dummyMessage);
                showToast(TestingActivity.this, "Reminder dihidupkan pukul 12:00 setiap hari");
            }
        });

        Button btnDisableReminder = findViewById(R.id.btn_disable_reminder_testing);
        btnDisableReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReminder(TestingActivity.this, REQUEST_DAILY_REMINDER);
                showToast(TestingActivity.this, "Reminder berhasil dimatikan");
            }
        });
    }
}