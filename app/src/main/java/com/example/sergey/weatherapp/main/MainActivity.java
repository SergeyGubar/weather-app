package com.example.sergey.weatherapp.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sergey.weatherapp.R;
import com.example.sergey.weatherapp.fragments.WeatherFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements MainActivityApi {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter mPresenter;
    private static final int LOCATION_PERMISSION = 123;
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longtitude";
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenter(this, this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION);
        } else {
            inflateFragment();
        }
    }

    public void inflateFragment() {
        final WeatherFragment mainFragment = new WeatherFragment();
        final Bundle args = new Bundle();
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    args.putDouble(LATITUDE_KEY, latitude);
                    args.putDouble(LONGITUDE_KEY, longitude);
                    mainFragment.setArguments(args);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.main_weather_container, mainFragment)
                            .commit();
                }
            });
        } catch (SecurityException ignore) {
            //permission anyway was given, idk why we should wrap it into try/catch
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int result = grantResults[0];
        if (result == PackageManager.PERMISSION_GRANTED) {
            inflateFragment();
        } else {
            Log.v(TAG, "Give me a permission!");
        }
    }


}
