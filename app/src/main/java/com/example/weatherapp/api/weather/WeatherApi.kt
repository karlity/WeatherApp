package com.example.weatherapp.api.weather

import com.example.weatherapp.api.models.Period
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherApi {

    @GET
    fun getPeriods(
        @Url url: String
    ) : Response<Period>
}