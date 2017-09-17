package com.example.sergey.weatherapp.entities

import java.util.*

/**
 * Created by Sergey on 9/12/2017.
 */
data class Weather(val time: Date, val summary: String,  private val _temperature : Int,
                   val humidity: Double, val pressure: Double, val icon : String) {
    val temperature : String get() = _temperature.toString() + "°C"
}