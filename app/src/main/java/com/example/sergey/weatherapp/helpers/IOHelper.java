package com.example.sergey.weatherapp.helpers;

import android.content.Context;

/**
 * Created by Sergey on 9/24/2017.
 */

public interface IOHelper {
    public void writeToCache(String fileName, String data, Context ctx);
    public String getDataFromCache(String fileName, Context ctx);
}
