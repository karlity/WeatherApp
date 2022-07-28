package com.example.weatherapp

import com.example.weatherapp.api.MoshiDeserializer
import com.example.weatherapp.api.models.Period
import com.example.weatherapp.api.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val moshiDeserializer: MoshiDeserializer
) : UtilViewModel<MainActivityActions, MainActivityViewState>() {

    override val initialState: MainActivityViewState = MainActivityViewState()

    override suspend fun FlowCollector<MainActivityActions>.reduce(action: MainActivityActions) {
        when (action) {
            MainActivityActions.LoadPeriods -> {
                loadPeriods()
            }
        }
    }

    private fun loadPeriods() {
        val period = moshiDeserializer.getPeriods()

        period?.let {
            setState {
                it.copy(viewList = assignLottie(period.periods))
            }
        }
    }

    private fun assignLottie(list: List<Period>): List<WeatherViewHolder> {
        val newList: MutableList<WeatherViewHolder> = mutableListOf()

        // If there was some kind of weather identifier, I would have used that to assign icons instead of the name
        list.map {
            val lottieIcon = when (it.name) {
                "This Afternoon" -> R.raw.sunny
                "Tonight" -> R.raw.cloudynight
                "Tuesday" -> R.raw.sunny
                "Tuesday Night" -> R.raw.rainynight
                "Wednesday" -> R.raw.sunny
                "Wednesday Night" -> R.raw.night
                else -> R.raw.windy
            }
            newList.add(WeatherViewHolder(it, lottieIcon))
        }
        return newList
    }
}

data class WeatherViewHolder(
    val period: Period,
    val icon: Int
)


sealed class MainActivityActions {
    object LoadPeriods : MainActivityActions()
}