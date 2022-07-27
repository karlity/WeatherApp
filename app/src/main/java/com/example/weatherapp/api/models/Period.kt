package com.example.weatherapp.api.models

data class Period(
    val name : String,
    val temperature: Int,
    val temperatureUnit: Char,
    val windSpeed: String,
    val windDirection: Char,
    val shortForecast: String
)
