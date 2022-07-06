package com.main.home_guard_droid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service {

    Notifications notifications = new Notifications();
    //Context context = getApplicationContext();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                @Override
                public void run() {
                    while(true){
                        Log.e("Service", "Home guard jest włączony");
                        try{
                            Thread.sleep(2000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        ).start();

        NotificationChannel channel = new NotificationChannel(
                "Background Service",
                "Background Service",
                NotificationManager.IMPORTANCE_LOW
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "dangerPush")
                .setContentTitle("Home guard jest włączony")
                //.setContentText("SPRAWDŹ HOME GUARD")
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        startForeground(3, notification.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}