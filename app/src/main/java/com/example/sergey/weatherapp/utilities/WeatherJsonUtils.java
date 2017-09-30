package com.example.sergey.weatherapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.sergey.weatherapp.contracts.SharedPrefContract;
import com.example.sergey.weatherapp.helpers.ConvertHelper;
import com.example.sergey.weatherapp.helpers.SharedPrefHelper;
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

    private Context mCtx;
    private SharedPrefHelper mPrefHelper;
    public WeatherJsonUtils(Context mCtx) {
        this.mCtx = mCtx;
        this.mPrefHelper = new SharedPrefUtils(mCtx);
    }

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
        String unit = mPrefHelper.getDegreeUnits();
        int temp;

        if(unit.equals(SharedPrefContract.CELCIUS)) {
            temp = converter.fahrenheitToCelsius(Double.valueOf(parsedTemp));
        } else {
            temp = (int) Math.round(Double.valueOf(parsedTemp));
        }

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


            String unit = mPrefHelper.getDegreeUnits();
            int maxTemp;
            int minTemp;
            if(unit.equals(SharedPrefContract.CELCIUS)) {
                maxTemp = converter.fahrenheitToCelsius(Double.valueOf(parsedMaxTemp));
                minTemp = converter.fahrenheitToCelsius(Double.valueOf(parsedMinTemp));
            } else {
                maxTemp = (int) Math.round(Double.valueOf(parsedMaxTemp));
                minTemp = (int) Math.round(Double.valueOf(parsedMinTemp));
            }


            double humidity = Double.valueOf(parsedHumidity);

            double pressure = Double.valueOf(parsedPressure);
            weatherToAdd = new DailyWeather(date, parsedSummary, parsedIconType, maxTemp - minTemp, humidity,
                    pressure, maxTemp, minTemp);
            result.add(weatherToAdd);
        }
        return result;
    }


}
