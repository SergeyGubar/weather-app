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
import android.util.Log;

import com.example.sergey.weatherapp.R;
import com.example.sergey.weatherapp.entities.CurrentWeather;
import com.example.sergey.weatherapp.entities.DailyWeather;
import com.example.sergey.weatherapp.entities.Weather;
import com.example.sergey.weatherapp.fragments.WeatherFragment;
import com.example.sergey.weatherapp.utilities.WeatherRecyclerAdapter;
import com.example.sergey.weatherapp.utilities.WeatherUtilites;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MainActivityApi {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityPresenter mPresenter;
    private static final int LOCATION_PERMISSION = 123;
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longtitude";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private WeatherRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    //FIXME : THAT'S BAD VERY BAD
    private double mLongitude;
    private double mLatitude;
    boolean locationReceived;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenter(this, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.weather_recycler_view);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mAdapter = new WeatherRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

    public void inflateFragment() {
        final WeatherFragment mainFragment = new WeatherFragment();
        final Bundle args = new Bundle();
        try {
            final LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (!locationReceived) {
                        mLongitude = location.getLongitude();
                        mLatitude = location.getLatitude();
                        String request = DailyWeatherTask.MAIN_URI + DailyWeatherTask.KEY +
                                mLatitude + "," + mLongitude + DailyWeatherTask.PARAMETERS;
                        new DailyWeatherTask().execute(request);
                        args.putDouble(LATITUDE_KEY, mLatitude);
                        args.putDouble(LONGITUDE_KEY, mLongitude);
                        mainFragment.setArguments(args);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.main_weather_container, mainFragment)
                                .commit();
                        locationReceived = true;
                        locationManager.removeUpdates(this);
                    }
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
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

    //FIXME : 2 ASYNC TASK WTF?
    public class DailyWeatherTask extends AsyncTask<String, Void, String> {
        private OkHttpClient mClient;
        private final String TAG = DailyWeatherTask.class.getSimpleName();
        public static final String MAIN_URI = "https://api.darksky.net/forecast/";
        public static final String KEY = "5b0ccf2ae41ee32686d2ae27eff06011/";
        public static final String PARAMETERS = "?exclude=hourly,minutely/";

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
                try {
                    List<DailyWeather> weather = WeatherUtilites.getDailyWeather(result);
                    mAdapter.setData(weather);
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing failed");
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Result is empty or null");
            }
        }
    }
}
