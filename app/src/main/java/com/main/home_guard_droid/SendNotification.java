package com.main.home_guard_droid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SendNotification {

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void sendNotificationIfDangerDetected(Context context) {

/*        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
            //RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.no)


            NotificationChannel channel = new NotificationChannel(
                    "dangerPush",
                    "dangerPush1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            //NotificationManagerCompat.from(this).createNotificationChannel(channel);
/*        if(getApplicationContext()==null){

        }*/
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "dangerPush")
                    //.setContentIntent(pendingIntent)
                    .setContentTitle("WYKRYTO ZAGROŻENIE")
                    .setContentText("SPRAWDŹ HOME GUARD")
                    .setSmallIcon(R.drawable.ic_danger_notification)
                    .setAutoCancel(false);

            /*@SuppressLint("ServiceCast") NotificationManagerCompat notificationManagerCompat = (NotificationManagerCompat) getSystemService(NOTIFICATION_SERVICE);*/
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            NotificationManagerCompat.from(context).notify(2, notification.build());
        }
}
