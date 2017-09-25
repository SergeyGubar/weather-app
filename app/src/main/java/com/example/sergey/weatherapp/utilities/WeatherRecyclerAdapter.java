package com.example.sergey.weatherapp.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergey.weatherapp.helpers.IconHelper;
import com.example.sergey.weatherapp.R;
import com.example.sergey.weatherapp.entities.DailyWeather;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Sergey on 9/17/2017.
 */

public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder> {

    private Context mCtx;
    private List<DailyWeather> mWeatherList;

    public WeatherRecyclerAdapter(Context ctx) {
        mCtx = ctx;
    }

    public WeatherRecyclerAdapter(Context ctx, List<DailyWeather> weatherList) {
        this.mCtx = ctx;
        this.mWeatherList = weatherList;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View inflatedView = inflater.inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        DailyWeather weather = mWeatherList.get(position);
        holder.setWeatherData(weather);
    }

    @Override
    public int getItemCount() {
        if (mWeatherList == null) {
            return 0;
        } else {
            return mWeatherList.size();
        }
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon;
        private TextView mTemperature;
        private TextView mSummary;
        private IconHelper mIconHelper;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.item_image_view);
            mTemperature = (TextView) itemView.findViewById(R.id.item_temperature_text_view);
            mSummary = (TextView) itemView.findViewById(R.id.item_description_tex_view);
            mIconHelper = new IconUtils();
        }

        public void setWeatherData(DailyWeather weather) {
            String summary = weather.getSummary();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE");

            String temperature = (weather.getMinTemperature() + " - " + weather.getMaxTemperature()
                    + "°C \n" + simpleDateFormat.format(weather.getTime()));
            String icon = weather.getIcon();
            mSummary.setText(summary);
            mTemperature.setText(temperature);
            int iconRes = mIconHelper.getWeatherIcon(icon);
            mIcon.setImageResource(iconRes);

        }
    }

    public void setData(List<DailyWeather> data) {
        mWeatherList = data;
        notifyDataSetChanged();
    }

}
