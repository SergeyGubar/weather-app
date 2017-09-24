package com.example.sergey.weatherapp.Helpers;

import com.example.sergey.weatherapp.entities.CurrentWeather;
import com.example.sergey.weatherapp.entities.DailyWeather;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Sergey on 9/24/2017.
 */

public interface WeatherJsonHelper {
    CurrentWeather getCurrentWeather(String json) throws JSONException;
    List<DailyWeather> getDailyWeatherFromJson(String json) throws JSONException;
}
