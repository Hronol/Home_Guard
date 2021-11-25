package com.main.home_guard_droid;

import android.app.Application;
import android.content.Context;

public class Sensors extends Application {
    public boolean flameDetection = false;
    public boolean gasDetection = false;
    public boolean buzzDetection = false;

    public boolean isFlameDetection() {
        return flameDetection;
    }

    public void setFlameDetection(boolean flameDetection) {
        this.flameDetection = flameDetection;
    }

    public boolean isGasDetection() {
        return gasDetection;
    }

    public void setGasDetection(boolean gasDetection) {
        this.gasDetection = gasDetection;
    }

    public boolean isBuzzDetection() {
        return buzzDetection;
    }

    public void setBuzzDetection(boolean buzzDetection) {
        this.buzzDetection = buzzDetection;
    }
}
