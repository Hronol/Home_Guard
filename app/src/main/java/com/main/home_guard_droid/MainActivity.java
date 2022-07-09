package com.main.home_guard_droid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Sensors sensors = new Sensors();
    Intent i;
    private Boolean flameStatus = true;
    private Boolean humidStatus = true;
    private Boolean tempStatus = true;
    private Boolean gasStatus = true;
    private Boolean buzzStatus = true;
    //DatabaseConnector databaseConnector = new DatabaseConnector();

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
        turnOnFlame();
        turnOnBuzz();
        turnOnGas();
        turnOnHumid();
        turnOnTemp();
        backgroundWatcher();
        //databaseConnector.getList();

    }

    public void setRealTimeData(){
        TextView tempDataTextView = (TextView) findViewById(R.id.tempDataTextView);
        TextView tempStatusTextView = (TextView) findViewById(R.id.tempStatusTextView);
        TextView humidDataTextView = (TextView) findViewById(R.id.humidDataTextView);
        TextView humidStatusTextView = (TextView) findViewById(R.id.humidStatusTextView);
        TextView flameDataTextView = (TextView) findViewById(R.id.flameDataTextView);
        TextView flameStatusTextView = (TextView) findViewById(R.id.flameStatusTextView);
        TextView gasDataTextView = (TextView) findViewById(R.id.gasDataTextView);
        TextView gasStatusTextView = (TextView) findViewById(R.id.gasStatusTextView);

    }

/*    public void goToHistory() {
        Button historybtn = (Button) findViewById(R.id.button_history);
        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
    }*/

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

    public void turnOnTemp() {
        // Sensors sensors = (Sensors) getApplicationContext();
        Button tempButton = (Button) findViewById(R.id.buttonTemp);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tempStatus) {
                    tempStatus = true;
                    tempButton.setText("TEMPERATURE - ONLINE");
                    //i.putExtra("gas", true);
                } else {
                    tempStatus = false;
                    tempButton.setText("TEMPERATURE - OFFLINE");
                    //i.putExtra("gas", false);
                    //startActivity(i);
                }
            }
        });

    }

    public void turnOnHumid() {
        // Sensors sensors = (Sensors) getApplicationContext();
        Button humidButton = (Button) findViewById(R.id.buttonHumid);
        humidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!humidStatus) {
                    humidStatus = true;
                    humidButton.setText("HUMIDITY - ONLINE");
                    //i.putExtra("gas", true);
                } else {
                    humidStatus = false;
                    humidButton.setText("HUMIDITY - OFFLINE");
                    //i.putExtra("gas", false);
                    //startActivity(i);
                }
            }
        });

    }

    public void turnOnBuzz() {
        // Sensors sensors = (Sensors) getApplicationContext();
        Button buzzButton = (Button) findViewById(R.id.buttonBuzz);
        buzzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buzzStatus) {
                    buzzStatus = true;
                    buzzButton.setText("BUZZER - ONLINE");
                    //i.putExtra("gas", true);
                } else {
                    buzzStatus = false;
                    buzzButton.setText("BUZZER - OFFLINE");
                    //.putExtra("gas", false);
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

    public void backgroundWatcher(){

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(DatabaseWorkManager.class, 15, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
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