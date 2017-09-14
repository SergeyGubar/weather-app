package com.example.sergey.weatherapp.main;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sergey.weatherapp.entities.Weather;
import com.example.sergey.weatherapp.utilities.WeatherUtilites;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sergey on 9/14/2017.
 */

public class MainActivityPresenter {
    private final Context mCtx;
    private final MainActivityApi mApi;

    public MainActivityPresenter(Context mCtx, MainActivityApi mApi) {
        this.mCtx = mCtx;
        this.mApi = mApi;
    }


}
