package com.example.sergey.weatherapp.main;

import android.content.Context;
import android.util.Log;

import com.example.sergey.weatherapp.entities.DailyWeather;
import com.example.sergey.weatherapp.utilities.IOUtilities;
import com.example.sergey.weatherapp.utilities.WeatherUtilites;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Sergey on 9/14/2017.
 */

public class MainActivityPresenter {
    private final Context mCtx;
    private final MainActivityApi mApi;
    private static final String TAG = MainActivityPresenter.class.getSimpleName();

    public MainActivityPresenter(Context mCtx, MainActivityApi mApi) {
        this.mCtx = mCtx;
        this.mApi = mApi;
    }

    public void loadDailyDataFromCache(String cacheFileName) {
        String cachedData = IOUtilities.getDataFromCache(cacheFileName, mCtx);
        if(cachedData != null) {
            try {
                List<DailyWeather> weatherList = WeatherUtilites.getDailyWeather(cachedData);
                mApi.getAdapter().setData(weatherList);
            } catch (JSONException e) {
                Log.e(TAG, "Json parse failed");
                e.printStackTrace();
            }
        }
    }



}
