package com.example.sergey.weatherapp.main;

import android.content.Context;

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
