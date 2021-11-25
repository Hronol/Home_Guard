package com.main.home_guard_droid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Database> {

    private static final String TAG = "ListViewAdapter";
    private LayoutInflater inflater;
    //private static ArrayList<Database> dblist;
    private int viewResourceId;
    private Context mContext;
    int mResource;

    public ListViewAdapter(Context mContext, int viewResourceId, ArrayList<Database> dbList){
        super(mContext, viewResourceId, dbList);
        this.viewResourceId = viewResourceId;
        //this.mContext = context;
    }

    public ListViewAdapter(Context mContext, ArrayList<Database> dbList){
        super(mContext,0, dbList);
    }

    private static class ViewHolder{
        TextView tvTemp;
        TextView tvDate;
        TextView tvTime;
        TextView tvWarning;
    }


/*    @Override
    public int getCount(){
        return dbList.size();
    }

    @Override
    public Database getItem(int position){
        return dbList.get(position);
    }*/

    @Override
    public long getItemId(int position){
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parents){
        String temp = getItem(position).getTemp();
        String date = getItem(position).getDay();
        String time = getItem(position).getTime();
        String warning = getItem(position).getWarning();
        Integer flame = getItem(position).getFlame();
        Integer gas = getItem(position).getGas();

        if(flame == 0 && gas == 0){
            warning = "OK";
        }

        Database db = new Database(temp, date, time, warning);

        final View result;

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(viewResourceId, null);
            holder = new ViewHolder();
            holder.tvTemp = (TextView) convertView.findViewById(R.id.textTemp);
            holder.tvDate = (TextView) convertView.findViewById(R.id.textDate);
            holder.tvTime = (TextView) convertView.findViewById(R.id.textTime);
            holder.tvWarning = (TextView) convertView.findViewById(R.id.textViewWarning);

            result = convertView;
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        if (db != null) {
/*
            tvTemp = (TextView) convertView.findViewById(R.id.textTemp);
            tvDate = (TextView) convertView.findViewById(R.id.textDate);
            tvTime = (TextView) convertView.findViewById(R.id.textTime);
            tvWarning = (TextView) convertView.findViewById(R.id.textViewWarning);
*/
            holder.tvTemp.setText(db.getTemp());
            holder.tvDate.setText(db.getDay());
            holder.tvTime.setText(db.getTime());
            holder.tvWarning.setText(db.getWarning());    //ogarnac warning w tym miejscu!!!
        }
        return convertView;
    }

/*    public void bindData(Database database, String key){
        tvTemp.setText(database.getTemp());
        tvDate.setText(database.getDay());
        tvTime.setText(database.getTime());
    }*/
}

