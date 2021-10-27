package com.main.home_guard_droid;

public class Database {

    private String temp;
    private String day;
    private String time;
    private String gas;
    private String flame;


    public Database(String temp, String day, String time, String gas, String flame) {
        this.temp = temp;
        this.day = day;
        this.time = time;
        this.gas = gas;
        this.flame = flame;
    }

    public Database(String temp, String day, String time) {
        this.temp = temp;
        this.day = day;
        this.time = time;
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

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getFlame() {
        return flame;
    }

    public void setFlame(String flame) {
        this.flame = flame;
    }
}
