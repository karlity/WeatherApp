package com.example.weatherapp

import android.content.Context
import com.example.weatherapp.api.MoshiDeserializer
import com.example.weatherapp.api.models.Period
import com.example.weatherapp.api.models.Periods
import com.example.weatherapp.api.weather.WeatherApi
import com.example.weatherapp.api.weather.WeatherRepository
import com.squareup.moshi.Moshi
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class HomeViewTest {

    //Just adding this to flex the networking setup. Not used in actual implementation
    private val weatherApi: WeatherApi = mockk(relaxed = true)
    private val weatherRepository: WeatherRepository = spyk(WeatherRepository(weatherApi))

    private val moshiDeserializer: MoshiDeserializer = mockk(relaxed = true)

    private lateinit var mainActivityViewModel: MainActivityViewModel

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Before
    fun before() {
        mainActivityViewModel = MainActivityViewModel(
            weatherRepository,
            moshiDeserializer
        )
    }

    @Test
    fun `load weather`() {
        coEvery { moshiDeserializer.getPeriods() } coAnswers {
            Periods(
                listOf(
                    Period(
                        name = "This Afternoon",
                        temperature = 75,
                        temperatureUnit = 'F',
                        windSpeed = "20 mph",
                        windDirection = "N",
                        shortForecast = "Mostly Sunny"
                    ),
                    Period(
                        name = "Tonight",
                        temperature = 53,
                        temperatureUnit = 'F',
                        windSpeed = "15 to 20 mph",
                        windDirection = "S",
                        shortForecast = "Partly Cloudy",
                    ),
                    Period(
                        name = "Tuesday",
                        temperature = 77,
                        temperatureUnit = 'F',
                        windSpeed = "15 to 20 mph",
                        windDirection = "S",
                        shortForecast = "Sunny",
                    ),
                    Period(
                        name = "Tuesday Night",
                        temperature = 47,
                        temperatureUnit = 'F',
                        windSpeed = "10 to 15 mph",
                        windDirection = "SW",
                        shortForecast = "Slight Chance Showers And Thunderstorms",
                    ),
                    Period(
                        name = "Wednesday",
                        temperature = 60,
                        temperatureUnit = 'F',
                        windSpeed = "10 to 15 mph",
                        windDirection = "W",
                        shortForecast = "Sunny",
                    ),
                    Period(
                        name = "Wednesday Night",
                        temperature = 38,
                        temperatureUnit = 'F',
                        windSpeed = "10 to 15 mph",
                        windDirection = "NW",
                        shortForecast = "Mostly Clear",
                    ),
                )
            )
        }

        var latestCollectedState = MainActivityViewState()

        val updatedState = MainActivityViewState(
            viewList = weatherList
        )

        runTest {
            launch {
                mainActivityViewModel.startViewModel(MainActivityActions.LoadPeriods)

                mainActivityViewModel.state.collectLatest {
                    latestCollectedState = it
                }
            }

            //Needed to allow the launched job to complete
            testScheduler.advanceUntilIdle()

            //Cancels the [actionflow]'s childjob in UtilViewmodel
            currentCoroutineContext().cancelChildren()

            assertEquals(updatedState, latestCollectedState)
        }
    }


    private val weatherList = listOf(
        WeatherViewHolder(
            Period(
                name = "This Afternoon",
                temperature = 75,
                temperatureUnit = 'F',
                windSpeed = "20 mph",
                windDirection = "N",
                shortForecast = "Mostly Sunny"
            ),
            icon = R.raw.sunny
        ),
        WeatherViewHolder(
            Period(
                name = "Tonight",
                temperature = 53,
                temperatureUnit = 'F',
                windSpeed = "15 to 20 mph",
                windDirection = "S",
                shortForecast = "Partly Cloudy",
            ),
            icon = R.raw.cloudynight
        ),
        WeatherViewHolder(
            Period(
                name = "Tuesday",
                temperature = 77,
                temperatureUnit = 'F',
                windSpeed = "15 to 20 mph",
                windDirection = "S",
                shortForecast = "Sunny",
            ),
            icon = R.raw.sunny
        ),
        WeatherViewHolder(
            Period(
                name = "Tuesday Night",
                temperature = 47,
                temperatureUnit = 'F',
                windSpeed = "10 to 15 mph",
                windDirection = "SW",
                shortForecast = "Slight Chance Showers And Thunderstorms",
            ),
            icon = R.raw.rainynight
        ),
        WeatherViewHolder(
            Period(
                name = "Wednesday",
                temperature = 60,
                temperatureUnit = 'F',
                windSpeed = "10 to 15 mph",
                windDirection = "W",
                shortForecast = "Sunny",
            ),
            icon = R.raw.sunny
        ),
        WeatherViewHolder(
            Period(
                name = "Wednesday Night",
                temperature = 38,
                temperatureUnit = 'F',
                windSpeed = "10 to 15 mph",
                windDirection = "NW",
                shortForecast = "Mostly Clear",
            ),
            icon = R.raw.night
        ),
    )
}