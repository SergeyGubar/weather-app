package com.example.sergey.weatherapp.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sergey.weatherapp.R;
import com.example.sergey.weatherapp.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity implements MainActivityApi {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter mPresenter;
    private static final int LOCATION_PERMISSION = 123;
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

        int hasLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(hasLocationPermission == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Everything is ok");
        } else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , LOCATION_PERMISSION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int result = grantResults[0];
        if(result == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission was granted!");

        } else {
            Log.v(TAG, "Fuck you, give me a permission !!!");
        }
    }



}
