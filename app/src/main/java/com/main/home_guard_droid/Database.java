package com.main.home_guard_droid;

public class Database {

    public String temp;
    public String humid;
    public String day;
    public String time;
    public String gas;
    public String flame;
    public String warning;


    public Database(String temp, String humid, String day, String time, String gas, String flame) {
        this.temp = temp;
        this.humid = humid;
        this.day = day;
        this.time = time;
        this.gas = gas;
        this.flame = flame;
    }

    public Database() {
    }

    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
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

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
