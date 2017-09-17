package com.example.sergey.weatherapp.entities;

import java.util.Date;

/**
 * Created by Sergey on 9/17/2017.
 */

public abstract class Weather {
    private Date mTime;
    private String mSummary;
    private String mIcon;
    private int mTemperature;
    private double mHumidity;
    private double mPressure;

    public Weather(Date mTime, String mSummary, String mIcon, int mTemperature, double mHumidity, double mPressure) {
        this.mTime = mTime;
        this.mSummary = mSummary;
        this.mIcon = mIcon;
        this.mTemperature = mTemperature;
        this.mHumidity = mHumidity;
        this.mPressure = mPressure;
    }

    public Date getTime() {
        return mTime;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getTemperature() {
        return mTemperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public double getPressure() {
        return mPressure;
    }
}
