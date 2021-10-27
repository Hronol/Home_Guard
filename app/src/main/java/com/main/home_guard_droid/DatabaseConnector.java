package com.main.home_guard_droid;

import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector{

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceDB;
    private ArrayList<Database> sensorsValue = new ArrayList<>();
    public Database database = new Database();
    String temp;
    String day;
    String time;
    String flame;
    String gas;

    public DatabaseConnector(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceDB = mDatabase.getReference("test1");
    }

    public DatabaseConnector(DatabaseReference mReferenceDB){
        this.mReferenceDB = mReferenceDB;
    }

    public interface DataStatus{
        void LoadedData(List<Database> sensorsValue, List<String> keys);
        void InsertedData();
        void UpdatedData();
        void DeletedData();
    }

/*    public void getData(final DataStatus dataStatus){
        mReferenceDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sensorsValue.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Database database = keyNode.getValue(Database.class);
                    sensorsValue.add(database);
                }
                dataStatus.LoadedData(sensorsValue, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String errors = error.toString();
            }
        });
    }*/

    public ArrayList<Database> getList(){
        mReferenceDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //fetchData(snapshot);
                //sensorsValue.clear();
                //Database database = new Database();
                //database = snapshot.getValue(Database.class);
                //sensorsValue.add(snapshot.getValue(Database.class));

                String temp = snapshot.child("Temp").getValue().toString();
                String day = snapshot.child("day").getValue().toString();
                String time = snapshot.child("time").getValue().toString();
                String flame = snapshot.child("flame").getValue().toString();
                String gas = snapshot.child("gas").getValue().toString();

                Database database = new Database(temp, day, time, flame, gas);

                sensorsValue.add(database);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //fetchData(snapshot);
                Database database = new Database();
                database = snapshot.getValue(Database.class);
                sensorsValue.add(database);
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
        return sensorsValue;
    }

/*    public void fetchData(DataSnapshot snapshot){
        sensorsValue.clear();
        for (DataSnapshot keyNode : snapshot.getChildren()){
            Database database = keyNode.getValue(Database.class);
            sensorsValue.add(database);
        }
    }*/
}