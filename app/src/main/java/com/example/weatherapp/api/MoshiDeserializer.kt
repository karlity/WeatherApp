package com.example.weatherapp.api

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.api.models.Periods
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okio.buffer
import okio.source
import javax.inject.Inject

class MoshiDeserializer @Inject constructor(
    private val appContext: Context,
    private val moshi: Moshi
) {

    fun getPeriods() : Periods? {

        val adapter = moshi.adapter(Periods::class.java)
        val obj = appContext.resources.openRawResource(R.raw.weather).source()
        var jsonReader = JsonReader.of(obj.buffer())


        return adapter.fromJson(jsonReader)
    }

}