package com.example.sergey.weatherapp.utilities;

import com.example.sergey.weatherapp.R;
import com.example.sergey.weatherapp.entities.CurrentWeather;
import com.example.sergey.weatherapp.entities.DailyWeather;
import com.example.sergey.weatherapp.entities.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergey on 9/14/2017.
 */

public class WeatherUtilites {
    //This class absolutely breaks Single responsibility principle. That's bad, i know
    private WeatherUtilites() {

    }

    public static int fahrenheitToCelcius(double fahrenheitDegree) {
        return (int) ((fahrenheitDegree - 32) / 1.8);
    }

    public static CurrentWeather getCurrentWeather(String json) throws JSONException {
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

    public static List<DailyWeather> getDailyWeather(String json) throws JSONException {
        List<DailyWeather> result = new ArrayList<>();
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

            int minTemp = WeatherUtilites.fahrenheitToCelcius(Double.valueOf(parsedMinTemp));
            int maxTemp = WeatherUtilites.fahrenheitToCelcius(Double.valueOf(parsedMaxTemp));

            double humidity = Double.valueOf(parsedHumidity);

            double pressure = Double.valueOf(parsedPressure);
            //FIXME: Temp
            weatherToAdd = new DailyWeather(date, parsedSummary, parsedIconType, 0, humidity,
                    pressure, maxTemp, minTemp);
            result.add(weatherToAdd);
        }
        return result;
    }

    public static int getWeatherIcon(String iconName) {
        switch (iconName) {
            case "clear-day":
                return R.drawable.ic_clear_day;
            case "clear-night":
                return R.drawable.ic_clear_night;
            case "rain":
                return R.drawable.ic_rain;
            case "snow":
                return R.drawable.ic_snow;
            case "sleet":
                return R.drawable.ic_rain;
            case "wind":
                return R.drawable.ic_wind;
            case "fog":
                return R.drawable.ic_fog;
            default:
                return R.drawable.ic_cloud;
        }
    }

}
