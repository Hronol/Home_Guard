package com.main.home_guard_droid;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {

    public String tempValue;
    public String dayValue;
    public String timeValue;
    public String warningValue;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public DatabaseHelper() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public String getTempValue() {
        return tempValue;
    }

    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    public String getDayValue() {
        return dayValue;
    }

    public void setDayValue(String dayValue) {
        this.dayValue = dayValue;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public String getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(String warningValue) {
        this.warningValue = warningValue;
    }

    public DatabaseHelper(String tempValue, String dayValue, String timeValue, String warningValue) {
        this.tempValue = tempValue;
        this.dayValue = dayValue;
        this.timeValue = timeValue;
        this.warningValue = warningValue;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("temp", tempValue);
        result.put("day", dayValue);
        result.put("time", timeValue);
        result.put("warning", warningValue);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
}
