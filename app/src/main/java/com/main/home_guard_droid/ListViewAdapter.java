package com.main.home_guard_droid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<Database> dbList;
    private int viewResourceId;
    private Context mContext;
    int mResource;
    private TextView tvTemp;
    private TextView tvDate;
    private TextView tvTime;


/*    public ListViewAdapter (Context context, int textViewResourceID, ArrayList<Database>dbList) {
        super(context, textViewResourceID, dbList);
        this.dbList = dbList;
        mContext = context;
        mResource = textViewResourceID;
    }*/

    public ListViewAdapter(Context mContext, ArrayList<Database> dbList){
        this.mContext = mContext;
        this.dbList = dbList;
    }

    @Override
    public int getCount(){
        return dbList.size();
    }

    @Override
    public Object getItem(int position){
        return dbList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        if(convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(mResource,parents,false);
        }

        tvTemp = (TextView)convertView.findViewById(R.id.textTemp);
        tvDate = (TextView)convertView.findViewById(R.id.textDate);
        tvTime = (TextView)convertView.findViewById(R.id.textTime);

        Database db = (Database) this.getItem(position);

        tvTemp.setText(db.getTemp());
        tvDate.setText(db.getDay());
        tvTime.setText(db.getTime());

        return convertView;
    }

    public void bindData(Database database, String key){
        tvTemp.setText(database.getTemp());
        tvDate.setText(database.getDay());
        tvTime.setText(database.getTime());
    }
}

