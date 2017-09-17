package com.example.sergey.weatherapp.entities;

import com.example.sergey.weatherapp.utilities.WeatherUtilites;

import java.util.Date;

/**
 * Created by Sergey on 9/17/2017.
 */

public class DailyWeather extends Weather {
    private String maxTemperature;
    private String minTemperature;

    public DailyWeather(Date time, String summary, String icon, int temperature, double humidity,
                        double pressure, int maxTemp, int minTemp) {
        super(time, summary, icon, temperature, humidity, pressure);
        maxTemperature = String.valueOf(WeatherUtilites.fahrenheitToCelcius(maxTemp));
        minTemperature = String.valueOf(WeatherUtilites.fahrenheitToCelcius(minTemp));
    }

    public String getMaxTemperature() {
        return maxTemperature + "°C ";
    }

    public String getMinTemperature() {
        return minTemperature + "°C ";
    }
}
