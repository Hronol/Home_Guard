package com.main.home_guard_droid;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ListViewAdapter extends ArrayAdapter<Database> {

    private static final String TAG = "ListViewAdapter";
    private LayoutInflater inflater;
    //private static ArrayList<Database> dblist;
    private int viewResourceId;
    private Context mContext;
    int mResource;
    Notifications notifications = new Notifications();
    Date currentTime = Calendar.getInstance().getTime();
    //SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yy");
    //SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
    StringBuilder stringBuilder = new StringBuilder();

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, View convertView, ViewGroup parents){
        String temp = getItem(position).getTemp();
        String date = getItem(position).getDay();
        String time = getItem(position).getTime();
        String warning = getItem(position).getWarning();
        Integer flame = getItem(position).getFlame();
        Integer gas = getItem(position).getGas();

        //Dates comparision
       // String dayToday = dayFormat.format(currentTime);
        //String timeToday = timeFormat.format(currentTime);
        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        String dateToday = dateFormat.format(currentTime);

        String dbDate = stringBuilder.append(date).append(" ").append(time).toString();

        if(flame == 0 && gas == 0){
            warning = "OK";
            //notifications.dangerDetected(warning);

        } else if(flame == 1 || gas == 1){
            warning = "DANGER";
            //notifications.dangerDetected(warning);
            if(dbDate.compareTo(dateToday) < 0) {
                notifications.sendNotificationIfDangerDetected();
            }
        }

        Database db = new Database(temp, date, time, warning);
        dbDate.equals("");
        stringBuilder.setLength(0);
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
           // if(flame == 0 && gas == 0){
           //     holder.tvWarning.setText("OK");
           // } else {
            holder.tvWarning.setText(db.getWarning());
            //}
        }
        return convertView;
    }





/*    public void bindData(Database database, String key){
        tvTemp.setText(database.getTemp());
        tvDate.setText(database.getDay());
        tvTime.setText(database.getTime());
    }*/
}

