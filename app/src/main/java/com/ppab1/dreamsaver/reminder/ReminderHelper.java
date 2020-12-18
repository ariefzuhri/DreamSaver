package com.ppab1.dreamsaver.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.MainActivity;

public class ReminderHelper {
    public static final String EXTRA_TITLE_NOTIF = "extra_title_notif";
    public static final String EXTRA_MESSAGE_NOTIF = "extra_message_notif";
    public static final int REQUEST_DAILY_REMINDER = 100;

    public static void sendNotification(Context context, int requestCode, String title, String message){
        String CHANNEL_ID = "channel_01";
        String CHANNEL_NAME = "Reminder";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQUEST_DAILY_REMINDER, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_logo_notif)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSound(defaultSoundUri)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = builder.build();
        if (notificationManager != null) notificationManager.notify(requestCode, notification);
    }

    public static void cancelReminder(Context context, int requestCode){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) alarmManager.cancel(pendingIntent);
    }
}
