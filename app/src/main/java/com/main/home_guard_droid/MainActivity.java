package com.main.home_guard_droid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Sensors sensors = new Sensors();
    Intent i;
    private Boolean flameStatus = true;
    private Boolean gasStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        i = new Intent(MainActivity.this, HistoryActivity.class);
        goToHistory();
        turnOnFlame();
        //turnOnBuzz();
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
        Button flameButton = (Button)findViewById(R.id.buttonFlame);
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
                    flameStatus = false;
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

}