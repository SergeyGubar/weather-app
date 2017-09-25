package com.example.sergey.weatherapp.utilities;

import com.example.sergey.weatherapp.helpers.ConvertHelper;

/**
 * Created by Sergey on 9/24/2017.
 */

public class DegreeConverter implements ConvertHelper {
    @Override
    public int fahrenheitToCelcius(double fahrenheitDegree) {
        return (int) ((fahrenheitDegree - 32) / 1.8);
    }
}
