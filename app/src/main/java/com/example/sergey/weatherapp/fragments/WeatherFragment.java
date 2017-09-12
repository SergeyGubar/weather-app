package com.example.sergey.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergey.weatherapp.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Sergey on 9/12/2017.
 */

public class WeatherFragment extends android.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boolean isAttachedToParent = false;
        View inflatedView = inflater.inflate(R.layout.main_weather_fragment, container, isAttachedToParent);
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        return inflatedView;
    }
}
