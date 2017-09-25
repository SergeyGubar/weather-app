package com.example.sergey.weatherapp.utilities;

import com.example.sergey.weatherapp.helpers.ConvertHelper;
import com.example.sergey.weatherapp.helpers.WeatherJsonHelper;
import com.example.sergey.weatherapp.entities.CurrentWeather;
import com.example.sergey.weatherapp.entities.DailyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergey on 9/14/2017.
 */

public class WeatherJsonUtils implements WeatherJsonHelper {

    public CurrentWeather getCurrentWeatherFromJson(String json) throws JSONException {
        ConvertHelper converter = new DegreeConverter();
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

        int temp = converter.fahrenheitToCelcius(Double.valueOf(parsedTemp));

        double humidity = Double.valueOf(parsedHumidity);

        double pressure = Double.valueOf(parsedPressure);

        CurrentWeather weather = new CurrentWeather(date, parsedSummary, temp, humidity, pressure, parsedIconType);
        return weather;
    }

    public List<DailyWeather> getDailyWeatherFromJson(String json) throws JSONException {
        List<DailyWeather> result = new ArrayList<>();
        ConvertHelper converter = new DegreeConverter();
        JSONObject mainObj = new JSONObject(json);
        JSONObject dailyObj = mainObj.getJSONObject("daily");
        JSONArray dailyArr = dailyObj.getJSONArray("data");
        DailyWeather weatherToAdd;
        for(int i = 0; i < dailyArr.length(); i++) {
            JSONObject weather = (JSONObject) dailyArr.get(i);
            String parsedDate = weather.getString("time");
            String parsedSummary = weather.getString("summary");
            String parsedMinTemp = weather.getString("temperatureLow");
            String parsedMaxTemp = weather.getString("temperatureHigh");
            String parsedHumidity = weather.getString("humidity");
            String parsedPressure = weather.getString("pressure");
            String parsedIconType = weather.getString("icon");
            long milliSeconds = Long.valueOf(parsedDate) * 1000;
            Date date = new Date(milliSeconds);

            int minTemp = converter.fahrenheitToCelcius(Double.valueOf(parsedMinTemp));
            int maxTemp = converter.fahrenheitToCelcius(Double.valueOf(parsedMaxTemp));

            double humidity = Double.valueOf(parsedHumidity);

            double pressure = Double.valueOf(parsedPressure);
            weatherToAdd = new DailyWeather(date, parsedSummary, parsedIconType, maxTemp - minTemp, humidity,
                    pressure, maxTemp, minTemp);
            result.add(weatherToAdd);
        }
        return result;
    }


}
