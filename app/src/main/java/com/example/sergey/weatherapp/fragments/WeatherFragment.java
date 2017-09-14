package com.example.sergey.weatherapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sergey.weatherapp.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Sergey on 9/12/2017.
 */

public class WeatherFragment extends Fragment {

    private TextView mTemperatureTextView;
    private OkHttpClient mClient;
    private static final String TAG  = "WeatherFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boolean isAttachedToParent = false;
        View inflatedView = inflater.inflate(R.layout.main_weather_fragment, null, isAttachedToParent);
        mClient = new OkHttpClient();
        mTemperatureTextView = (TextView) inflatedView.findViewById(R.id.degrees_text_view);

        new WeatherTask().execute("https://api.darksky.net/forecast/5b0ccf2ae41ee32686d2ae27eff06011/" +
                "49.96535591,36.2878418?exclude=hourly,minutely/");
        return inflatedView;
    }

    public class WeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String queryUrl = params[0];
            Request request = new Request.Builder()
                    .url(queryUrl)
                    .build();

            Response response = null;
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
            if(result != null && !result.isEmpty()) {
                Log.d(TAG, result);
            } else {
                Log.d(TAG, "ATTENTION SOMETHIN WENT WRONG VERY WRONG!!");
            }
        }
    }


}
