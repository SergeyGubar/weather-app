package com.example.sergey.weatherapp.fragments;

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
import com.example.sergey.weatherapp.entities.CurrentWeather;
import com.example.sergey.weatherapp.helpers.IconHelper;
import com.example.sergey.weatherapp.helpers.WeatherJsonHelper;
import com.example.sergey.weatherapp.utilities.IconUtils;
import com.example.sergey.weatherapp.utilities.WeatherJsonUtils;

import org.json.JSONException;

import static com.example.sergey.weatherapp.main.MainActivity.WEATHER_KEY;


/**
 * Created by Sergey on 9/12/2017.
 */

public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private TextView mTemperatureTextView;
    private TextView mCityTextView;
    private TextView mSummaryTextView;
    private ImageView mWeatherImageView;
    private WeatherJsonHelper mJsonHelper;
    private IconHelper mIconHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boolean isAttachedToParent = false;
        View inflatedView = inflater.inflate(R.layout.main_weather_fragment, null, isAttachedToParent);

        Bundle args = getArguments();
        String weatherData  = args.getString(WEATHER_KEY);

        mTemperatureTextView = (TextView) inflatedView.findViewById(R.id.degrees_text_view);
        mCityTextView = (TextView) inflatedView.findViewById(R.id.city_text_view);
        mSummaryTextView = (TextView) inflatedView.findViewById(R.id.weather_description_text_view);
        mWeatherImageView = (ImageView) inflatedView.findViewById(R.id.weather_image_view);
        mJsonHelper = new WeatherJsonUtils();
        mIconHelper = new IconUtils();
        setWeather(weatherData);

        return inflatedView;
    }

    private void setWeather(String jsonWeatherResponse) {
        CurrentWeather weather;
        try {
            weather = mJsonHelper.getCurrentWeatherFromJson(jsonWeatherResponse);
            mTemperatureTextView.setText(weather.getTemperature());
            mSummaryTextView.setText(weather.getSummary());
            int icon = mIconHelper.getWeatherIcon(weather.getIcon());
            mWeatherImageView.setImageResource(icon);
            mCityTextView.setText("Now, " + "Kharkiv");
        } catch (JSONException e) {
            Log.e(TAG, "Json parse failed!");
            e.printStackTrace();
        }
    }


}
