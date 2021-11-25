package com.main.home_guard_droid;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications {

    public String channel_id = "channelID";
    public String channel_name = "channelName";
    public Context mcontext;

    public void createNotification(Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                .setContentTitle("Home Guard")
                .setContentText("UWAGA! Wykryto zagrożenie!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(mcontext);
        managerCompat.notify(1, builder.build());
    }

    public void createNotificationChannel(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("channelDescription");

            NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}


