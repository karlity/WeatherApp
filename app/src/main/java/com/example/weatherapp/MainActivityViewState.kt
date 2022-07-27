package com.example.weatherapp

import com.example.weatherapp.api.models.Period
import com.example.weatherapp.api.models.Periods

data class MainActivityViewState(
    val periods: Periods? = null
)