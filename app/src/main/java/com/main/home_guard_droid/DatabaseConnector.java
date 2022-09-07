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

public class DatabaseConnector {

    Notifications notifications = new Notifications();
    DatabaseReference dbOnChildAdded;
    DatabaseReference dbBuzzerRef;
    Database sensorsValues = new Database();
    Boolean buzzerStatus = true;
    int tempInt;
    int humidInt;

    public DatabaseConnector() {
        dbOnChildAdded = FirebaseDatabase.getInstance().getReference().child("test");
        dbBuzzerRef = FirebaseDatabase.getInstance().getReference().child("Buzzer");
    }

    public void pushBuzzerstatus(Boolean buzzerStatus){
        if(buzzerStatus != null) {
            dbBuzzerRef.child("buzzer").setValue(buzzerStatus);
        }
    }

    public Boolean getBuzzerStatus(){
        dbBuzzerRef.child("buzzer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                buzzerStatus = dataSnapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return buzzerStatus;
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
                    if (tempInt >= 40 || humidInt <=25){
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