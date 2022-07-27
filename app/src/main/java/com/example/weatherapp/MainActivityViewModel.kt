package com.example.weatherapp

import com.example.weatherapp.api.ApiResponse
import com.example.weatherapp.api.MoshiDeserializer
import com.example.weatherapp.api.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val moshiDeserializer: MoshiDeserializer
) : UtilViewModel<MainActivityActions, MainActivityViewState>() {

    override val initialState: MainActivityViewState = MainActivityViewState()

    override suspend fun FlowCollector<MainActivityActions>.reduce(action: MainActivityActions) {
        when(action) {
            MainActivityActions.LoadPeriods -> {
                loadPeriods()
            }
        }
    }

    private suspend fun loadPeriods() {
      val period =  moshiDeserializer.getPeriods()

        setState {
            it.copy(periods = period)
        }
    }
}

sealed class MainActivityActions() {
    object LoadPeriods : MainActivityActions()
}