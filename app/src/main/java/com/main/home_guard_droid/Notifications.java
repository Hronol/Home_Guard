package com.main.home_guard_droid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;

public class Notifications extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotificationIfDangerDetected(Context context) {

        Intent notifyIntent = new Intent(context, MainActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent,
                0
        );

        RemoteViews notificationLayoutExpanded = new RemoteViews(context.getPackageName(), R.layout.layout_notification);

        NotificationChannel channel = new NotificationChannel(
                "dangerPush",
                "dangerPush1",
                NotificationManager.IMPORTANCE_HIGH
        );

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "dangerPush")
                .setContentTitle("WYKRYTO ZAGROŻENIE")
                .setContentText("SPRAWDŹ HOME GUARD")
                .setSmallIcon(R.drawable.ic_danger_notification)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        NotificationManagerCompat.from(context).notify(2, notification.build());
    }

}



