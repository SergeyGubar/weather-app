package com.example.sergey.weatherapp.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sergey on 9/17/2017.
 */

public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder> {

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        public WeatherViewHolder(View itemView) {
            super(itemView);
        }
    }

}
