package com.example.sergey.weatherapp.utilities;

import com.example.sergey.weatherapp.entities.CurrentWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Sergey on 9/14/2017.
 */

public class WeatherUtilites {
    private WeatherUtilites() {

    }

    public static int fahrenheitToCelcius(double fahrenheitDegree) {
        return (int) ((fahrenheitDegree - 32) / 1.8);
    }

    public static CurrentWeather getWeatherFromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject currentWeather = jsonObject.getJSONObject("currently");

        String parsedDate = currentWeather.getString("time");
        String parsedSummary = currentWeather.getString("summary");
        String parsedTemp = currentWeather.getString("temperature");
        String parsedHumidity = currentWeather.getString("humidity");
        String parsedPressure = currentWeather.getString("pressure");
        String parsedIconType = currentWeather.getString("icon");
        long milliSeconds = Long.valueOf(parsedDate) * 1000;
        Date date = new Date(milliSeconds);

        int temp = WeatherUtilites.fahrenheitToCelcius(Double.valueOf(parsedTemp));

        double humidity = Double.valueOf(parsedHumidity);

        double pressure = Double.valueOf(parsedPressure);

        CurrentWeather weather = new CurrentWeather(date, parsedSummary, temp, humidity, pressure, parsedIconType);
        return weather;
    }

}
