package com.example.sergey.weatherapp.entities

import java.util.*

/**
 * Created by Sergey on 9/12/2017.
 */
class CurrentWeather(time: Date, summary: String, _temperature : Int,
                          humidity: Double,  pressure: Double,  icon : String) : Weather(time, summary,
        icon, _temperature, humidity, pressure) {
    val temperature : String get() = getTemperature().toString()
}