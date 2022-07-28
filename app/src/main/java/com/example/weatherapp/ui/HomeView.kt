package com.example.weatherapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.MainActivityActions
import com.example.weatherapp.MainActivityViewModel
import com.example.weatherapp.R
import com.example.weatherapp.WeatherViewHolder
import timber.log.Timber

@Composable
fun HomeView(viewModel: MainActivityViewModel) {
    if (!viewModel.isInitialized) {
        viewModel.startViewModel(MainActivityActions.LoadPeriods)
    }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Text(
            text = stringResource(id = R.string.weather),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
        state.viewList?.let {
            PeriodView(list = it)
        }
    }
}

@Composable
fun PeriodView(list: List<WeatherViewHolder>) {

    LazyColumn {

        itemsIndexed(
            items = list,
            key = { index, item -> item.period.name }
        ) { index, period ->
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(period.icon))
            val progress by animateLottieCompositionAsState(composition)

            Card(
                shape = RoundedCornerShape(18.dp),
                backgroundColor = Color.DarkGray,
                elevation = 4.dp,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {


                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = period.period.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)

                            )
                            Text(
                                text = "${period.period.temperature} ${period.period.temperatureUnit}\u00B0",
                                color = Color.White,
                                fontSize = 16.sp,

                                )
                            Text(
                                text = "${stringResource(id = R.string.wind_speed)} ${period.period.windSpeed}",
                                color = Color.White,

                                )
                            Text(
                                text = "${stringResource(id = R.string.wind_direction)} ${period.period.windDirection}",
                                color = Color.White,
                            )

                        }
                        LottieAnimation(
                            composition = composition, modifier = Modifier
                                .size(100.dp)
                                .padding(10.dp)
                                .align(Alignment.CenterVertically),
                            progress = { progress }
                        )
                        Spacer(modifier = Modifier.padding(0.dp, 0.dp))

                    }
                    Text(
                        text = period.period.shortForecast,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 10.dp)
                    )
                }
            }
        }
    }
}