package com.example.weatherapp.api.models

data class Periods(
    val periods: List<Period>
)

data class Period(
    val name : String,
    val temperature: Int,
    val temperatureUnit: Char,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String
)
