package com.main.home_guard_droid;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DatabaseConnector {

    Notifications notifications = new Notifications();
    DatabaseReference dbDataChange;
    DatabaseReference dbOnChildAdded;
    DatabaseReference dbBuzzerRef;
    Database sensorsValues = new Database();
    int tempInt;
    int humidInt;

    public DatabaseConnector() {
        //dbDataChange = FirebaseDatabase.getInstance().getReference().child("test").child("-N6j2oqmGlDzEdz99ewJ");
        dbOnChildAdded = FirebaseDatabase.getInstance().getReference().child("test");
        dbBuzzerRef = FirebaseDatabase.getInstance().getReference().child("Buzzer");
    }

    public void checkRPIConnection(){

    }

/*    public Boolean checkBuzzerStatus(Boolean buzzerStatus){
        if(buzzerStatus != null) {
            Boolean buzzStatus = dbBuzzerRef.child("buzzer").get();
            if( buzzStatus == true){
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }*/

    public void pushBuzzerstatus(Boolean buzzerStatus){
        if(buzzerStatus != null) {
            dbBuzzerRef.child("buzzer").setValue(buzzerStatus);
        }
    }

    public Database getValues(Context context, boolean flameStatus, boolean gasStatus) {
        dbDataChange.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

/*                String temp = snapshot.child("temp").getValue().toString();
                String date = snapshot.child("day").getValue().toString();
                String time = snapshot.child("time").getValue().toString();
                String flame = snapshot.child("flame").getValue().toString();
                String gas = snapshot.child("gas").getValue().toString();
                String humidity = snapshot.child("wilg").getValue().toString();

                if (flame.equals("0") && gas.equals("0")) {
                    flame = "OK";
                    gas = "OK";
                } else if (flame.equals("1") && gas.equals("1")) {
                    flame = "DANGER";
                    gas = "DANGER";
                    notifications.sendNotificationIfDangerDetected(context);
                } else if (flame.equals("1") || gas.equals("1")) {
                    if (flame.equals("1") && flameStatus) {
                        flame = "DANGER";
                        gas = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    } else if (gas.equals("1") && gasStatus) {
                        gas = "DANGER";
                        flame = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    }
                }

                sensorsValues = new Database(temp, humidity, date, time, gas, flame);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return sensorsValues;
    }

    public Database getList(Context context, boolean flameStatus, boolean gasStatus) {
        dbOnChildAdded.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String temp = snapshot.child("temp").getValue().toString();
                String date = snapshot.child("day").getValue().toString();
                String time = snapshot.child("time").getValue().toString();
                String flame = snapshot.child("flame").getValue().toString();
                String gas = snapshot.child("gas").getValue().toString();
                String humidity = snapshot.child("wilg").getValue().toString();

                if(temp != null && humidity != null){
                    tempInt = Integer.parseInt(temp.substring(0, temp.length()-4));
                    humidInt = Integer.parseInt(humidity.substring(0, humidity.length() - 4));
                }

                if (flame.equals("0") && gas.equals("0")) {
                    flame = "OK";
                    gas = "OK";
                    if (tempInt >= 50 || humidInt <=5){
                        notifications.sendNotificationIfDangerDetected(context);
                    }
                } else if ((flame.equals("1") && gas.equals("1")) && (flameStatus || gasStatus)) {
                    flame = "DANGER";
                    gas = "DANGER";
                    notifications.sendNotificationIfDangerDetected(context);
                } else if (flame.equals("1") || gas.equals("1")) {
                    if (flame.equals("1") && flameStatus) {
                        flame = "DANGER";
                        gas = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    } else if (gas.equals("1") && gasStatus) {
                        gas = "DANGER";
                        flame = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    }
                }

                sensorsValues = new Database(temp, humidity, date, time, gas, flame);
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

        return sensorsValues;
    }
}