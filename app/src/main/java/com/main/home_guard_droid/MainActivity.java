package com.main.home_guard_droid;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private Boolean flameStatus = true;
    private Boolean humidStatus = true;
    private Boolean tempStatus = true;
    private Boolean gasStatus = true;
    private Boolean buzzStatus = true;
    private String rpiStatus = "ONLINE";
    private String getRPITime = "";
    private String getRPIDate = "";
    Notifications notifications = new Notifications();
    LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("CET"));
    LocalDateTime dateTimeToCompare = localDateTime.minusMinutes(5);
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    DateTimeFormatter dateTimeFormatForTime = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter dateTimeFormatForDate = DateTimeFormatter.ofPattern("dd/MM/yy");
    DatabaseConnector databaseConnector = new DatabaseConnector();
    Database realtimeDBhelper = new Database();

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!backgroundServiceRunning()) {
            Intent intent = new Intent(this, BackgroundService.class);
            startForegroundService(intent);
        }

        //registerToken();
        turnOnFlame();
        turnOnBuzz();
        turnOnGas();
        turnOnHumid();
        turnOnTemp();
        backgroundWatcher();
        //backgroundWatcher2();
        //setRealTimeData();
        //databaseConnector.getList();

    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            public void run() {
                handler.postDelayed(runnable, delay);
                realtimeDBhelper = databaseConnector.getList(MainActivity.this, flameStatus, gasStatus);
                //realtimeDBhelper = databaseConnector.getValues(MainActivity.this, flameStatus, gasStatus);
                setRealTimeData(realtimeDBhelper);
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

    public Boolean getFlameStatus(){
        return flameStatus;
    }

    public Boolean getGasStatus(){
        return gasStatus;
    }

/*    public static MainActivity getInstance() {
        return instance;
    }*/

/*    public void checkBuzzerStatus(){
        TextView buzzerDataTextView = (TextView) findViewById(R.id.buzzerDataTextView);
        TextView buzzerStatusTextView = (TextView) findViewById(R.id.buzzerStatusTextView);

        if (buzzStatus) {
            if (database.get() == null) {
                buzzerDataTextView.setText("OFFLINE");
            } else {
                buzzerDataTextView.setText(database.getGas());
                buzzerStatusTextView.setText("ONLINE");
            }
        }
    }*/

    //push db
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void setRealTimeData(Database database) {
        TextView tempDataTextView = (TextView) findViewById(R.id.tempDataTextView);
        TextView tempStatusTextView = (TextView) findViewById(R.id.tempStatusTextView);
        TextView humidDataTextView = (TextView) findViewById(R.id.humidDataTextView);
        TextView humidStatusTextView = (TextView) findViewById(R.id.humidStatusTextView);
        TextView flameDataTextView = (TextView) findViewById(R.id.flameDataTextView);
        TextView flameStatusTextView = (TextView) findViewById(R.id.flameStatusTextView);
        TextView gasDataTextView = (TextView) findViewById(R.id.gasDataTextView);
        TextView gasStatusTextView = (TextView) findViewById(R.id.gasStatusTextView);
        TextView buzzerDataTextView = (TextView) findViewById(R.id.buzzerDataTextView);
        TextView buzzerStatusTextView = (TextView) findViewById(R.id.buzzerStatusTextView);
        TextView RPIStatusTextView = (TextView) findViewById(R.id.rpiStatusTextview);

        if (tempStatus) {
            if (database.getTemp() == null) {
                tempDataTextView.setText("Loading data...");
            } else {
                tempDataTextView.setText(database.getTemp());
                String styleTempHumid = database.getTemp();
                int tempInt = Integer.parseInt(styleTempHumid.substring(0, styleTempHumid.length()-4));
                if(tempInt>=50){
                    tempDataTextView.setText("DANGER");
                } else {
                    tempDataTextView.setText(database.getTemp());
                }
                tempStatusTextView.setText("ONLINE");
            }
        }
        if (humidStatus) {
            if (database.getHumid() == null) {
                humidDataTextView.setText("Loading data...");
            } else {
                humidDataTextView.setText(database.getTemp());
                String styleTempHumid = database.getHumid();
                int humidInt = Integer.parseInt(styleTempHumid.substring(0, styleTempHumid.length() - 4));
                if (humidInt <= 5) {
                    humidDataTextView.setText("DANGER");
                } else {
                    humidDataTextView.setText(database.getHumid());
                }
                humidStatusTextView.setText("ONLINE");
            }
        }
        if (flameStatus) {
            if (database.getFlame() == null) {
                flameDataTextView.setText("Loading data...");
            } else {
                flameDataTextView.setText(database.getFlame());
                flameStatusTextView.setText("ONLINE");
            }
        }
        if (gasStatus) {
            if (database.getGas() == null) {
                gasDataTextView.setText("Loading data...");
            } else {
                gasDataTextView.setText(database.getGas());
                gasStatusTextView.setText("ONLINE");
            }
        }
        rpiStatus = database.getTime();
        if(rpiStatus != null) {
            getRPITime = database.getTime();
            getRPIDate = database.getDay();
            String compareTimeForRPICheck = getRPITime.substring(0, rpiStatus.length() - 3);
            String compareDateForRPICheck = getRPIDate;
            String formatTimeToCompare = dateTimeToCompare.format(dateTimeFormatForTime);
            String formatDateToCompare = localDateTime.format(dateTimeFormatForDate);
            timeFormat.setTimeZone(TimeZone.getTimeZone("CET"));

            if(compareDateForRPICheck.equals(formatDateToCompare)){
                int compare = compareTimeForRPICheck.compareTo(formatTimeToCompare);
                if(compare < 0){
                    RPIStatusTextView.setText("OFFLINE - RESTART HOME GUARD !!!");
                    notifications.sendNotificationIfDangerDetected(this);
                } else {
                    RPIStatusTextView.setText("ONLINE");
                }
            }
        }


    }

    public void turnOnFlame() {
        Button flameButton = (Button) findViewById(R.id.buttonFlame);
        TextView flameDataTextView = (TextView) findViewById(R.id.flameDataTextView);
        TextView flameStatusTextView = (TextView) findViewById(R.id.flameStatusTextView);
        flameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flameStatus) {
                    flameStatus = true;
                    flameButton.setText("FLAME - ONLINE");
                    flameStatusTextView.setText("Loading data...");
                    flameDataTextView.setText("Loading data...");
                } else {
                    flameStatus = false;
                    flameButton.setText("FLAME - OFFLINE");
                    flameStatusTextView.setText("OFFLINE");
                    flameDataTextView.setText("OFFLINE");
                }
            }
        });
    }

    public void turnOnGas() {
        TextView gasDataTextView = (TextView) findViewById(R.id.gasDataTextView);
        TextView gasStatusTextView = (TextView) findViewById(R.id.gasStatusTextView);
        Button gasButton = (Button) findViewById(R.id.buttonSmoke);
        gasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gasStatus) {
                    gasStatus = true;
                    gasButton.setText("SMOKE/GAS - ONLINE");
                    gasDataTextView.setText("Loading data...");
                    gasStatusTextView.setText("Loading data...");
                } else {
                    gasStatus = false;
                    gasButton.setText("SMOKE/GAS - OFFLINE");
                    gasDataTextView.setText("OFFLINE");
                    gasStatusTextView.setText("OFFLINE");
                }
            }
        });

    }

    public void turnOnTemp() {
        TextView tempDataTextView = (TextView) findViewById(R.id.tempDataTextView);
        TextView tempStatusTextView = (TextView) findViewById(R.id.tempStatusTextView);
        Button tempButton = (Button) findViewById(R.id.buttonTemp);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tempStatus) {
                    tempStatus = true;
                    tempButton.setText("TEMPERATURE - ONLINE");
                    tempDataTextView.setText("Loading data...");
                    tempStatusTextView.setText("Loading data...");
                } else {
                    tempStatus = false;
                    tempButton.setText("TEMPERATURE - OFFLINE");
                    tempDataTextView.setText("OFFLINE");
                    tempStatusTextView.setText("OFFLINE");
                }
            }
        });

    }

    public void turnOnHumid() {
        TextView humidDataTextView = (TextView) findViewById(R.id.humidDataTextView);
        TextView humidStatusTextView = (TextView) findViewById(R.id.humidStatusTextView);
        Button humidButton = (Button) findViewById(R.id.buttonHumid);
        humidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!humidStatus) {
                    humidStatus = true;
                    humidButton.setText("HUMIDITY - ONLINE");
                    humidDataTextView.setText("Loading data...");
                    humidStatusTextView.setText("Loading data...");
                } else {
                    humidStatus = false;
                    humidButton.setText("HUMIDITY - OFFLINE");
                    humidDataTextView.setText("OFFLINE");
                    humidStatusTextView.setText("OFFLINE");
                }
            }
        });

    }

    public void turnOnBuzz() {
        Button buzzButton = (Button) findViewById(R.id.buttonBuzz);
        buzzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buzzStatus) {
                    buzzStatus = true;
                    buzzButton.setText("BUZZER - ONLINE");
                    databaseConnector.pushBuzzerstatus(buzzStatus);
                } else {
                    buzzStatus = false;
                    buzzButton.setText("BUZZER - OFFLINE");
                    databaseConnector.pushBuzzerstatus(buzzStatus);
                }
            }
        });

    }

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
    public boolean backgroundServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (BackgroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void backgroundWatcher() {

        //Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(DatabaseWorkManager.class, 3, TimeUnit.SECONDS)
                //.setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
        //setRealTimeData(realtimeDBhelper);
    }
}