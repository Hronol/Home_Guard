package com.main.home_guard_droid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.execute("http://192.168.1.71");

        ListView listView = (ListView)findViewById(R.id.listview1);

        Database test = new Database("20", "01/01/21", "12.21.21");
        Database test2 = new Database("21", "02/02/21", "13.21.21");
        Database test3 = new Database("22", "03/03/21", "14.21.21");

        ArrayList<Database> databaseArrayList = new ArrayList<>();
        databaseArrayList.add(test);
        databaseArrayList.add(test2);
        databaseArrayList.add(test3);

        ListViewAdapter adapter = new ListViewAdapter(this, R.layout.adapter_listview, databaseArrayList);
        listView.setAdapter(adapter);
    }
}