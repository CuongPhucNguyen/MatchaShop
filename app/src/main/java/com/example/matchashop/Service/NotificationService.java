package com.example.matchashop.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.matchashop.R;

public class NotificationService  extends Service {
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationBinder binder = new NotificationBinder();
    private NotificationManager notificationManager;
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Connected to Notification Service", Toast.LENGTH_SHORT).show();
        return binder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = getSystemService(NotificationManager.class);
        createNotificationChannels();
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            notificationManager.createNotificationChannel(channel1);
        }

    }

    public void sendNotification(String title, String message)
    {
        Notification notification = new NotificationCompat.Builder(this,
                CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }

    public class NotificationBinder extends Binder {
    public NotificationService getService() {
        return NotificationService.this;
    }
    }
}
