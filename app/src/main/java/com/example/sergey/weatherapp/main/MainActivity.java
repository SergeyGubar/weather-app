package com.example.sergey.weatherapp.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.sergey.weatherapp.R;
import com.example.sergey.weatherapp.entities.DailyWeather;
import com.example.sergey.weatherapp.fragments.WeatherFragment;
import com.example.sergey.weatherapp.utilities.IOUtils;
import com.example.sergey.weatherapp.utilities.WeatherRecyclerAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MainActivityApi {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOCATION_PERMISSION = 123;
    public static final String WEATHER_KEY = "weather";
    private static final String CACHE_FILE_NAME = "weathercache";
    private static final String IS_RESTORED_KEY = "isRestored";
    private LocationManager mLocationManager;
    private WeatherRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private MainActivityPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPresenter = new MainActivityPresenter(this, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.weather_recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mAdapter = new WeatherRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //load fragment with data from cache
        mPresenter.getDailyWeatherFromCache(CACHE_FILE_NAME);
        final WeatherFragment mainFragment = new WeatherFragment();
        final Bundle args = new Bundle();
        args.putString(WEATHER_KEY, mPresenter.getDataFromCache(CACHE_FILE_NAME));
        mainFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_weather_container, mainFragment)
                .commit();
        Log.d(TAG, "Data restored from the cache");

        //if device was rotated or smth - do not load data from the internet, cached data is enough,
        //but in case of running the activity for the first time - load data
        if (savedInstanceState == null || !savedInstanceState.containsKey(IS_RESTORED_KEY)) {
            Log.d(TAG, "Data loaded from the internet");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            LOCATION_PERMISSION);
                } else {
                    inflateFragment();
                }
            } else {
                inflateFragment();
            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_RESTORED_KEY, true);
    }


    public void inflateFragment() {
        try {
            final LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double mLongitude = location.getLongitude();
                    double mLatitude = location.getLatitude();
                    String request = WeatherTask.MAIN_URI + WeatherTask.KEY +
                            mLatitude + "," + mLongitude + WeatherTask.PARAMETERS;
                    new WeatherTask().execute(request);
                    mLocationManager.removeUpdates(this);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    100, 0, listener);

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

    @Override
    public WeatherRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    private class WeatherTask extends AsyncTask<String, Void, String> {
        private final String TAG = WeatherTask.class.getSimpleName();
        static final String MAIN_URI = "https://api.darksky.net/forecast/";
        static final String KEY = "5b0ccf2ae41ee32686d2ae27eff06011/";
        static final String PARAMETERS = "?exclude=hourly,minutely/";
        private OkHttpClient mClient;

        @Override
        protected String doInBackground(String... params) {
            String queryUrl = params[0];
            mClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(queryUrl)
                    .build();

            Response response;
            try {
                response = mClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                mPresenter.writeToCache(CACHE_FILE_NAME, result);
                List<DailyWeather> weather = mPresenter.getDailyWeatherFromJson(result);
                mAdapter.setData(weather);
                final WeatherFragment mainFragment = new WeatherFragment();
                final Bundle args = new Bundle();
                args.putString(WEATHER_KEY, result);
                mainFragment.setArguments(args);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_weather_container, mainFragment)
                        .commit();
            } else {
                Log.e(TAG, "Result is empty or null");
            }
        }


    }
}
