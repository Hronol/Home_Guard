package com.main.home_guard_droid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications extends FirebaseMessagingService {

/*    public Notifications(Context baseContext){
        super(baseContext);
    }*/

    Context context = this;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        final String CHANNEL_ID = "channel";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "push",
                NotificationManager.IMPORTANCE_HIGH
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_danger_notification)
                .setAutoCancel(false);
        NotificationManagerCompat.from(this).notify(1, notification.build());
        super.onMessageReceived(remoteMessage);
    }

    public boolean dangerDetected(String isDanger){
        if(isDanger.equals("DANGER"))
            return true;
        else
            return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotificationIfDangerDetected(Context context){

/*        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
        //RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.no)
        RemoteViews notificationLayoutExpanded = new RemoteViews(context.getPackageName(), R.layout.layout_notification);

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
                .setCustomBigContentView(notificationLayoutExpanded)
                .setAutoCancel(false);

        /*@SuppressLint("ServiceCast") NotificationManagerCompat notificationManagerCompat = (NotificationManagerCompat) getSystemService(NOTIFICATION_SERVICE);*/
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        NotificationManagerCompat.from(context).notify(2, notification.build());
    }

    public void turnOnAlarmOnDanger(){

    }


/*    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification BackgroundNotification(Context context){

        NotificationChannel channel = new NotificationChannel(
                "Background Service",
                "Background Service",
                NotificationManager.IMPORTANCE_LOW
        );

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "dangerPush")
                .setContentTitle("Home guard jest włączony")
                //.setContentText("SPRAWDŹ HOME GUARD")
                .setSmallIcon(R.drawable.ic_danger_notification);
                //.setAutoCancel(false);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        NotificationManagerCompat notifyService = NotificationManagerCompat.from(context).notify(3, notification.build());

        return notifyService;
    }*/

}



