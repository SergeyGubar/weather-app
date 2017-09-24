package com.example.sergey.weatherapp.main;

import android.content.Context;
import android.util.Log;

import com.example.sergey.weatherapp.Helpers.IOHelper;
import com.example.sergey.weatherapp.Helpers.WeatherJsonHelper;
import com.example.sergey.weatherapp.entities.DailyWeather;
import com.example.sergey.weatherapp.utilities.IOUtils;
import com.example.sergey.weatherapp.utilities.WeatherJsonUtils;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Sergey on 9/14/2017.
 */

public class MainActivityPresenter {
    private final Context mCtx;
    private final MainActivityApi mApi;
    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private IOHelper mIoHelper;
    private WeatherJsonHelper mJsonHelper;


    public MainActivityPresenter(Context mCtx, MainActivityApi mApi) {
        this.mCtx = mCtx;
        this.mApi = mApi;
        mIoHelper = new IOUtils();
        mJsonHelper = new WeatherJsonUtils();
    }

    public void getDailyWeatherFromCache(String cacheFileName) {
        String cachedData = mIoHelper.getDataFromCache(cacheFileName, mCtx);
        if (cachedData != null) {
            try {
                List<DailyWeather> weatherList = mJsonHelper.getDailyWeatherFromJson(cachedData);
                mApi.getAdapter().setData(weatherList);
            } catch (JSONException e) {
                Log.e(TAG, "Json parse failed");
                e.printStackTrace();
            }
        }
    }

    public void writeToCache(String cacheFileName, String json) {
        mIoHelper.writeToCache(cacheFileName, json, mCtx);
    }

    public String getDataFromCache(String cacheName) {
        return mIoHelper.getDataFromCache(cacheName, mCtx);
    }

    public List<DailyWeather> getDailyWeatherFromJson(String json) {
        List<DailyWeather> result = null;
        try {
            result = mJsonHelper.getDailyWeatherFromJson(json);
        } catch (JSONException ex) {
            ex.printStackTrace();
            Log.e(TAG, "Json parse failed. Null is gonna be returned");
        }
        return result;
    }


}
