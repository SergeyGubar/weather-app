package com.example.sergey.weatherapp.utilities;

import com.example.sergey.weatherapp.Helpers.IconHelper;
import com.example.sergey.weatherapp.R;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Sergey on 9/24/2017.
 */

public class IconUtils implements IconHelper {

    private static Dictionary<String, Integer> icons = createHash();

    private static Dictionary<String, Integer> createHash() {
        Dictionary<String, Integer> hash = new Hashtable<>();
        icons.put("clear-day", R.drawable.ic_clear_day);
        icons.put("clear-night", R.drawable.ic_clear_night);
        icons.put("rain", R.drawable.ic_rain);
        icons.put("snow", R.drawable.ic_snow);
        icons.put("sleet", R.drawable.ic_rain);
        icons.put("wind", R.drawable.ic_wind);
        icons.put("fog", R.drawable.ic_fog);
        return hash;
    }

    @Override
    public int getWeatherIcon(String iconName) {
        return icons.get(iconName);
    }
}
