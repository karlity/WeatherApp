package com.example.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.MainActivityActions
import com.example.weatherapp.MainActivityViewModel

@Composable
fun HomeView(viewModel: MainActivityViewModel = hiltViewModel()) {
    if (!viewModel.isInitialized) {
        viewModel.startViewModel(MainActivityActions.LoadPeriods)
    }


}