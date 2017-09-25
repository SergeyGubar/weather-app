package com.example.sergey.weatherapp.helpers;

import com.example.sergey.weatherapp.entities.CurrentWeather;
import com.example.sergey.weatherapp.entities.DailyWeather;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Sergey on 9/24/2017.
 */

public interface WeatherJsonHelper {
    CurrentWeather getCurrentWeatherFromJson(String json) throws JSONException;
    List<DailyWeather> getDailyWeatherFromJson(String json) throws JSONException;
}
