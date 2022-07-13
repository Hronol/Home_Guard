package com.main.home_guard_droid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DatabaseConnector{

/*    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceDB;*/
    Notifications notifications = new Notifications();
    DatabaseReference db;
    //private ArrayList<Database> sensorsValue = new ArrayList<>();
    Date currentTime = Calendar.getInstance().getTime();
    //SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yy");
    //SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
    StringBuilder stringBuilder = new StringBuilder();
    Database sensorsValues = new Database();

    public DatabaseConnector(){
        //mDatabase = FirebaseDatabase.getInstance();
        //mReferenceDB = mDatabase.getReference();
        db = FirebaseDatabase.getInstance().getReference().child("test");
        //db = FirebaseDatabase.getInstance().getReference();
    }

/*    public DatabaseConnector(DatabaseReference mReferenceDB){
        this.mReferenceDB = mReferenceDB;
    }*/

/*    public ArrayList<Database> readData(){
        mReferenceDB.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot snapshot = task.getResult();

                        *//*String temp = snapshot.child("Temp").getValue().toString();
                        String day = snapshot.child("day").getValue().toString();
                        String time = snapshot.child("time").getValue().toString();
                        String flame = snapshot.child("flame").getValue().toString();
                        String gas = snapshot.child("gas").getValue().toString();*//*
                        Database database = new Database();
                        database = snapshot.getValue(Database.class);
                        sensorsValue.add(snapshot.getValue(Database.class));
                        //sensorsValue.add(new Database(temp,day,time,flame,gas));
                    }
                }
            }
        });
        return sensorsValue;
    }*/

/*    public void setRealTimeData(){
//, String humid, String flame, String gas, String day, String time
*//*        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("key1", temp);
        intent.putExtras(bundle);*//*

    }*/

    public Database getList(Context context){
        db.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
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

                //Database database = new Database(temp, day, time, flame, gas);

                String dbDate = stringBuilder.append(date).append(" ").append(time).toString();

                if(flame.equals("0") && gas.equals("0")){
                    warning = "OK";
                    flame = "OK";
                    gas = "OK";
                    //notifications.dangerDetected(warning);

                } else if(flame.equals("1") || gas.equals("1")){
                    warning = "DANGER";
                    notifications.sendNotificationIfDangerDetected(context);
                    if(flame.equals("1")){
                        flame = "DANGER";
                        gas = "OK";
                    } else if(gas.equals("1")){
                        gas = "DANGER";
                        flame = "OK";
                    }
                    //notifications.dangerDetected(warning);
/*                    if(dbDate.compareTo(dateToday) == 0) {
                        notifications.sendNotificationIfDangerDetected(context);
                    }*/
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

                if(flame.equals("0") && gas.equals("0")){
                    warning = "OK";
                    flame = "OK";
                    gas = "OK";
                    //notifications.dangerDetected(warning);

                } else if(flame.equals("1") || gas.equals("1")){
                    warning = "DANGER";
                    notifications.sendNotificationIfDangerDetected(context);
                    if(flame.equals("1")){
                        flame = "DANGER";
                        gas = "OK";
                    } else if(gas.equals("1")){
                        gas = "DANGER";
                        flame = "OK";
                    }
                    //notifications.dangerDetected(warning);
/*                    if(dbDate.compareTo(dateToday) == 0) {
                        notifications.sendNotificationIfDangerDetected(context);
                    }*/
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