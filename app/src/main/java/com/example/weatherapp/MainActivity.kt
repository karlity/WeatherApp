package com.example.weatherapp

import android.os.Bundle
import android.util.JsonReader
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStreamReader
import android.view.View
import android.widget.ListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var output = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obj = this.resources.openRawResource(R.raw.weather)
        var json = JsonReader(InputStreamReader(obj))

        json.beginObject()

        weatherreader(json)
        println(output)

        val adapter = ArrayAdapter<String>(this, R.layout.activity_listview, output)
        val listView: ListView = findViewById<View>(R.id.mobile_list) as ListView

        listView.adapter = adapter



    }

    fun weatherreader(json: JsonReader) {
        json.hasNext()
        if (json.nextName() == "periods") {
            json.beginArray()
            json.beginObject()
            parser(json)
            json.endObject()
            json.beginObject()
            parser(json)
            json.endObject()
            json.beginObject()
            parser(json)
            json.endObject()
            json.beginObject()
            parser(json)
            json.endObject()
            json.beginObject()
            parser(json)
            json.endObject()
        }
    }

    fun parser(json: JsonReader) {
        var tempStore = ""
        if (json.nextName() == "name") {
            val value = json.nextString()
            tempStore += "$value \n"
        }
        if (json.nextName() == "temperature") {
            val value = json.nextInt().toString()
            tempStore += value
        }
        if (json.nextName() == "temperatureUnit") {
            val value = json.nextString()
            tempStore += "$value \n"
        }
        if (json.nextName() == "windSpeed") {
            val value = json.nextString()
            tempStore += "$value "
        }
        if (json.nextName() == "windDirection") {
            val value = json.nextString()
            tempStore += "$value \n"
        }
        if (json.nextName() == "shortForecast") {
            val value = json.nextString()
            tempStore += "$value \n"
        }
        output.add(tempStore)
    }
}


