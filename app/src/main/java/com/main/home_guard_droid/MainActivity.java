package com.main.home_guard_droid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    Sensors sensors = new Sensors();
    Intent i;
    private Boolean flameStatus = true;
    private Boolean gasStatus = true;
    DatabaseConnector databaseConnector = new DatabaseConnector();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!backgroundServiceRunning()){
            Intent intent = new Intent(this, BackgroundService.class);
            startForegroundService(intent);
        }

        registerToken();
        i = new Intent(MainActivity.this, HistoryActivity.class);
        goToHistory();
        turnOnFlame();
        //turnOnBuzz();
        turnOnGas();
        //databaseConnector.getList();

    }

    public void goToHistory() {
        Button historybtn = (Button) findViewById(R.id.button_history);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
    }

    public void turnOnFlame() {
        Button flameButton = (Button) findViewById(R.id.buttonFlame);
        flameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flameStatus) {
                    flameStatus = true;
                    flameButton.setText("FLAME - ONLINE");
                    i.putExtra("flame", true);
                } else {
                    flameStatus = false;
                    flameButton.setText("FLAME - OFFLINE");
                    i.putExtra("flame", false);
                    //startActivity(i);
                }
            }
        });
       /* flameButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    i.putExtra("flame", true);
                    startActivity(i);
                    //sensors.setFlameDetection(true);
                } else {
                    //sensors.setFlameDetection(false);
                    i.putExtra("flame", false);
                    startActivity(i);
                }
            }
        });*/
    }

    public void turnOnGas() {
        // Sensors sensors = (Sensors) getApplicationContext();
        Button gasButton = (Button) findViewById(R.id.buttonSmoke);
        gasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gasStatus) {
                    gasStatus = true;
                    gasButton.setText("SMOKE/GAS - ONLINE");
                    i.putExtra("gas", true);
                } else {
                    gasStatus = false;
                    gasButton.setText("SMOKE/GAS - OFFLINE");
                    i.putExtra("gas", false);
                    //startActivity(i);
                }
            }
        });

    }

   /* public void turnOnBuzz() {
     //   Sensors sensors = (Sensors) getApplicationContext();
        Switch buzzSwitch = (Switch) findViewById(R.id.switch_buzz);
        buzzSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sensors.setBuzzDetection(true);
                } else {
                    sensors.setBuzzDetection(false);
                }
            }
        });
    }*/

    public void registerToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    task.getException();
                    return;
                }

                String token = task.getResult();

                String msg = getString(R.string.msg_token_fmt, token);
            }
        });
    }

    //zapobieganie kilkukrotnego uruchomienia instancji serwisu
    public boolean backgroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(BackgroundService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

/*    public void setFirebaseListener(){
        private FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        Bundle params = new Bundle();
        params.putString("image_name", "name");
        params.putString("full_text", "text");
        mFirebaseAnalytics.logEvent("share_image", params);
    }*/
}