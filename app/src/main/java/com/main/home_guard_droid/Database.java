package com.main.home_guard_droid;

public class Database {

    private String tempValue;
    private String day;
    private String time;

    public Database(String tempValue, String day, String time) {
        this.tempValue = tempValue;
        this.day = day;
        this.time = time;
    }

    public String getTempValue() {
        return tempValue;
    }

    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
