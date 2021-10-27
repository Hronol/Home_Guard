package com.main.home_guard_droid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    DatabaseReference db;
    DatabaseConnector connector;
    ListViewAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView)findViewById(R.id.listview1);

        db = FirebaseDatabase.getInstance().getReference();
        connector = new DatabaseConnector(db);

        adapter = new ListViewAdapter(this, connector.getList());
        listView.setAdapter(adapter);

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