package com.example.sergey.weatherapp.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sergey.weatherapp.R;
import com.example.sergey.weatherapp.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity implements MainActivityApi {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenter(this, this);
        WeatherFragment mainFragment = new WeatherFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_weather_container, mainFragment)
                .commit();
    }

}
