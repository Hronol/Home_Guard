package com.main.home_guard_droid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    public String channel_id = "channelID";
    public String channel_name = "channelName";
    Sensors sensors = new Sensors();
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        i = new Intent(MainActivity.this, HistoryActivity.class);
        createNotificationChannel();
        goToHistory();
        turnOnFlame();
        turnOnBuzz();
        turnOnGas();

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
       // Sensors sensors = (Sensors) getApplicationContext();
        Switch flameSwitch = (Switch) findViewById(R.id.switch_flame);
        flameSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });
    }

    public void turnOnGas() {
       // Sensors sensors = (Sensors) getApplicationContext();
        Switch gasSwitch = (Switch) findViewById(R.id.switch_smoke);
        gasSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sensors.setGasDetection(true);
                } else {
                    sensors.setGasDetection(false);
                }
            }
        });

    }

    public void turnOnBuzz() {
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
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("channelDescription");

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

    }
}