package com.example.sergey.weatherapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.sergey.weatherapp.contracts.SharedPrefContract;
import com.example.sergey.weatherapp.helpers.SharedPrefHelper;

/**
 * Created by Sergey on 9/30/2017.
 */

public class SharedPrefUtils implements SharedPrefHelper {
    private SharedPreferences mPrefs;
    private Context mCtx;


    public SharedPrefUtils(Context mCtx) {
        this.mCtx = mCtx;
    }

    @Override
    public String getDegreeUnits() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mCtx);
        String unit = mPrefs.getString(SharedPrefContract.UNITS_KEY, "celsius");
        return unit;
    }

    @Override
    public String getDegreeSign() {
        String units = getDegreeUnits();
        String degreeSign = "";
        if(units.equals(SharedPrefContract.CELCIUS)) {
            degreeSign = "°C";
        } else if(units.equals(SharedPrefContract.FAHRENHEIT)) {
            degreeSign = "°F";
        }
        return degreeSign;
    }
}
