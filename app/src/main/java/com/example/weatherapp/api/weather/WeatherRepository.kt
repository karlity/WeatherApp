package com.example.weatherapp.api.weather

import com.example.weatherapp.api.ApiResponse
import com.example.weatherapp.api.models.Period
import com.example.weatherapp.api.models.Periods
import com.example.weatherapp.api.toApiResponse
import javax.inject.Inject

//I wanted to demonstrate how I would go about getting the data if it lived in an API endpoint
class WeatherRepository @Inject constructor(
    val api: WeatherApi
) {

    suspend fun getPeriods() : ApiResponse<Periods> {
        return toApiResponse {
            api.getPeriods("/app/src/main/res/raw/weather.json")
        }
    }
}