package com.main.home_guard_droid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    //Notifications notifications = new Notifications();
    Bundle extras;
    ArrayAdapter<String> arrayAdapter2;
    ArrayAdapter<Database> arrayAdapter;
    DatabaseReference db;
    ArrayList<Database> arrayList = new ArrayList<Database>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ListViewAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = FirebaseDatabase.getInstance().getReference().child("test");
        listView = (ListView)findViewById(R.id.listview1);
        //arrayAdapter = new ArrayAdapter<Database>(this, android.R.layout.simple_list_item_1,arrayList);
        //Database test = new Database("20", "01/01/21", "12.21.21", "warning");
        //arrayList2.add(test);
        adapter = new ListViewAdapter(this, R.layout.adapter_listview, arrayList);
       // arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.adapter_listview, arrayList2);
        listView.setAdapter(adapter);

        db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Database value = snapshot.getValue(Database.class);
//test
                    extras = getIntent().getExtras();
                    if (extras != null){
                    if(extras.get("flame").equals(false)){
                        arrayList.add(value);
                        adapter.notifyDataSetChanged();
                    }
                    //notifications.createNotification();
                    }
                }

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


/*        Database test = new Database("20", "01/01/21", "12.21.21");
        Database test2 = new Database("21", "02/02/21", "13.21.21");
        Database test3 = new Database("22", "03/03/21", "14.21.21");*/


        //ArrayList<Database> databaseArrayList = new ArrayList<>();
/*        databaseArrayList.add(test);
        databaseArrayList.add(test2);
        databaseArrayList.add(test3);*/

        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, databaseArrayList);
        //listView.setAdapter(arrayAdapter);
        //arrayAdapter.notify();


        //ListViewAdapter adapter = new ListViewAdapter(this, R.layout.adapter_listview, databaseArrayList);
        //listView.setAdapter(adapter);
    }
}