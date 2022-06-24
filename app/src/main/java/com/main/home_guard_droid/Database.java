package com.main.home_guard_droid;

import android.content.Intent;

public class Database {

    public String temp;
    public String day;
    public String time;
    public Integer gas;
    public Integer flame;
    public String warning;


    public Database(String temp, String day, String time, Integer gas, Integer flame) {
        this.temp = temp;
        this.day = day;
        this.time = time;
        this.gas = gas;
        this.flame = flame;
    }

    public Database(String temp, String day, String time, String warning) {
        this.temp = temp;
        this.day = day;
        this.time = time;
        this.warning = warning;
    }

    public Database() {
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
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

    public Integer getGas() {
        return gas;
    }

    public void setGas(Integer gas) {
        this.gas = gas;
    }

    public Integer getFlame() {
        return flame;
    }

    public void setFlame(Integer flame) {
        this.flame = flame;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
