package com.example.sergey.weatherapp.utilities;

import android.app.ActivityManager;
import android.util.Log;

import com.example.sergey.weatherapp.helpers.IconHelper;
import com.example.sergey.weatherapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 9/24/2017.
 */

public class IconUtils implements IconHelper {

    private static Map<String, Integer> icons = createHash();
    private static final String TAG = IconUtils.class.getSimpleName();

    private static Map<String, Integer> createHash() {
        Map<String, Integer> hash = new HashMap<>();
        hash.put("clear-day", R.drawable.ic_clear_day);
        hash.put("clear-night", R.drawable.ic_clear_night);
        hash.put("partly-cloudy-day", R.drawable.ic_clear_day);
        hash.put("partly-cloudy-night", R.drawable.ic_clear_night);
        hash.put("rain", R.drawable.ic_rain);
        hash.put("snow", R.drawable.ic_snow);
        hash.put("sleet", R.drawable.ic_rain);
        hash.put("wind", R.drawable.ic_wind);
        hash.put("fog", R.drawable.ic_fog);
        return hash;
    }

    @Override
    public int getWeatherIcon(String iconName) {
        if (icons.containsKey(iconName)) {
            return icons.get(iconName);
        } else {
            Log.e(TAG, "NPE BOYZ " + iconName);
            return 0;
        }
    }
}
