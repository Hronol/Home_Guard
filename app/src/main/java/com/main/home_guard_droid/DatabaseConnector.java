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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DatabaseConnector {

    Notifications notifications = new Notifications();
    DatabaseReference db;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
    StringBuilder stringBuilder = new StringBuilder();
    Database sensorsValues = new Database();

    public DatabaseConnector() {
        //mDatabase = FirebaseDatabase.getInstance();
        //mReferenceDB = mDatabase.getReference();
        db = FirebaseDatabase.getInstance().getReference().child("test");
        //db = FirebaseDatabase.getInstance().getReference();
    }

    public Database getList(Context context, boolean flameStatus, boolean gasStatus) {
        db.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
                String dateToday = dateFormat.format(currentTime);

                String temp = snapshot.child("temp").getValue().toString();
                String warning = "";
                String date = snapshot.child("day").getValue().toString();
                String time = snapshot.child("time").getValue().toString();
                String flame = snapshot.child("flame").getValue().toString();
                String gas = snapshot.child("gas").getValue().toString();
                String humidity = snapshot.child("wilg").getValue().toString();


                String dbDate = stringBuilder.append(date).append(" ").append(time).toString();


                if (flame.equals("0") && gas.equals("0")) {
                    warning = "OK";
                    flame = "OK";
                    gas = "OK";
                }
                if (flame.equals("1") && gas.equals("1")) {
                    flame = "DANGER";
                    gas = "DANGER";
                    notifications.sendNotificationIfDangerDetected(context);
                } else if (flame.equals("1") || gas.equals("1")) {
                    warning = "DANGER";
                    if (flame.equals("1") && flameStatus) {
                        flame = "DANGER";
                        gas = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    }
                    if (gas.equals("1") && gasStatus) {
                        gas = "DANGER";
                        flame = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    }
                }

                sensorsValues = new Database(temp, humidity, date, time, gas, flame);
                //setRealTimeData();
                //sensorsValue.add(database);
                dbDate.equals("");
                stringBuilder.setLength(0);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //fetchData(snapshot);
/*                Database database = new Database();
                database = snapshot.getValue(Database.class);
                sensorsValue.add(database);*/
                //fetchData(snapshot);
                //sensorsValue.clear();
                //Database value = snapshot.getValue(Database.class);

                //sensorsValue.add(snapshot.getValue(Database.class));

                dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
                String dateToday = dateFormat.format(currentTime);

                String temp = snapshot.child("temp").getValue().toString();
                String warning = "";
                String date = snapshot.child("day").getValue().toString();
                String time = snapshot.child("time").getValue().toString();
                String flame = snapshot.child("flame").getValue().toString();
                String gas = snapshot.child("gas").getValue().toString();
                String humidity = snapshot.child("wilg").getValue().toString();

                String dbDate = stringBuilder.append(date).append(" ").append(time).toString();

                if (flame.equals("0") && gas.equals("0")) {
                    warning = "OK";
                    flame = "OK";
                    gas = "OK";
                }
                if (flame.equals("1") && gas.equals("1")) {
                    flame = "DANGER";
                    gas = "DANGER";
                    notifications.sendNotificationIfDangerDetected(context);
                } else if (flame.equals("1") || gas.equals("1")) {
                    warning = "DANGER";
                    if (flame.equals("1") && flameStatus) {
                        flame = "DANGER";
                        gas = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    }
                    if (gas.equals("1") && gasStatus) {
                        gas = "DANGER";
                        flame = "OK";
                        notifications.sendNotificationIfDangerDetected(context);
                    }
                }

                sensorsValues = new Database(temp, humidity, date, time, gas, flame);
                //setRealTimeData();
                //sensorsValue.add(database);
                dbDate.equals("");
                stringBuilder.setLength(0);
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

/*    public void fetchData(DataSnapshot snapshot){
        sensorsValue.clear();
        for (DataSnapshot keyNode : snapshot.getChildren()){
            Database database = keyNode.getValue(Database.class);
            sensorsValue.add(database);
        }
    }*/
}