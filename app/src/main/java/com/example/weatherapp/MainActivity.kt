package com.example.weatherapp

import android.os.Bundle
import android.util.JsonReader
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.HomeView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        val viewModel: MainActivityViewModel by viewModels()

        setContent {
                HomeView(viewModel = viewModel)
        }
    }


}


