package com.main.home_guard_droid;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Database> {

    private LayoutInflater inflater;
    private ArrayList<Database> dbList;
    private int viewResourceId;
    private Context mContext;
    int mResource;

/*    public ListViewAdapter (Context context, int textViewResourceID, ArrayList<Database>dbList){
        super(context,textViewResourceID,dbList);
        this.dbList = dbList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewResourceID;
    }*/

    public ListViewAdapter (Context context, int textViewResourceID, ArrayList<Database>dbList) {
        super(context, textViewResourceID, dbList);
        this.dbList = dbList;
        mContext = context;
        mResource = textViewResourceID;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        String temp = getItem(position).getTempValue();
        String date = getItem(position).getDay();
        String time = getItem(position).getTime();

        Database database = new Database(temp, date, time);

        inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parents, false);

        TextView tvTemp = (TextView)convertView.findViewById(R.id.textTemp);
        TextView tvDate = (TextView)convertView.findViewById(R.id.textDate);
        TextView tvTime = (TextView)convertView.findViewById(R.id.textTime);

        tvTemp.setText(temp);
        tvDate.setText(date);
        tvTime.setText(time);

        return convertView;
    }
}

