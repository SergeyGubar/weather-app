package com.example.sergey.weatherapp.utilities;

/**
 * Created by Sergey on 9/14/2017.
 */

public class WeatherUtilites {
    private WeatherUtilites() {

    }

    public static int fahrenheitToCelcius(double fahrenheitDegree) {
        return (int) ((fahrenheitDegree - 32) / 1.8);
    }
}
