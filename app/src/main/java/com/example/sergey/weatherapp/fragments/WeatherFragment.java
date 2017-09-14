package com.example.sergey.weatherapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergey.weatherapp.R;
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
 * Created by Sergey on 9/12/2017.
 */

public class WeatherFragment extends Fragment {

    private static final String TAG = "WeatherFragment";
    private TextView mTemperatureTextView;
    private TextView mCityTextView;
    private TextView mSummaryTextView;
    private ImageView mWeatherImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boolean isAttachedToParent = false;
        View inflatedView = inflater.inflate(R.layout.main_weather_fragment, null, isAttachedToParent);
        mTemperatureTextView = (TextView) inflatedView.findViewById(R.id.degrees_text_view);
        mCityTextView = (TextView) inflatedView.findViewById(R.id.city_text_view);
        mSummaryTextView = (TextView) inflatedView.findViewById(R.id.weather_description_text_view);
        mWeatherImageView = (ImageView) inflatedView.findViewById(R.id.weather_image_view);
        new WeatherTask().execute("https://api.darksky.net/forecast/5b0ccf2ae41ee32686d2ae27eff06011/" +
                "49.96535591,36.2878418?exclude=hourly,minutely/");
        return inflatedView;
    }

    private void setWeather(Weather weather) {
        mTemperatureTextView.setText(weather.getTemperature() + "°C");
        mSummaryTextView.setText(weather.getSummary());
        mCityTextView.setText("Kharkiv");
    }


    public class WeatherTask extends AsyncTask<String, Void, String> {
        private OkHttpClient mClient;
        private final String TAG = WeatherTask.class.getSimpleName();
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
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject currentWeather = jsonObject.getJSONObject("currently");

                    String parsedDate = currentWeather.getString("time");
                    String parsedSummary = currentWeather.getString("summary");
                    String parsedTemp = currentWeather.getString("temperature");
                    String parsedHumidity = currentWeather.getString("humidity");
                    String parsedPressure = currentWeather.getString("pressure");

                    long milliSeconds = Long.valueOf(parsedDate) * 1000;
                    Date date = new Date(milliSeconds);

                    int temp = WeatherUtilites.fahrenheitToCelcius(Double.valueOf(parsedTemp));

                    double humidity = Double.valueOf(parsedHumidity);

                    double pressure = Double.valueOf(parsedPressure);

                    Weather weather = new Weather(date, parsedSummary, temp, humidity, pressure);
                    Log.d(TAG, weather.toString());

                    setWeather(weather);

                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing failed");
                    e.printStackTrace();
                }

            } else {
                Log.d(TAG, "ATTENTION SOMETHING WENT WRONG VERY WRONG!!");
            }
        }
    }

}
