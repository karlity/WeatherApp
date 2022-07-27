package com.example.weatherapp.api.weather

import com.example.weatherapp.api.ApiResponse
import com.example.weatherapp.api.models.Period
import com.example.weatherapp.api.toApiResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    val api: WeatherApi
) {

    suspend fun getPeriods() : ApiResponse<Period> {
        return toApiResponse {
            api.getPeriods("/app/src/main/res/raw/weather.json")
        }
    }
}