package com.example.weatherapp.app.snackbar

import androidx.compose.material.SnackbarDuration
import androidx.compose.ui.graphics.Color
import com.example.weatherapp.R


object SnackbarOptions {
    val NoInternet = object : SnackbarCommand {
        override val messageResId: Int = R.string.wind_speed
        override val actionButtonTextResId: Int = R.string.weather
        override val color: Color = Color.LightGray
        override val duration: SnackbarDuration = SnackbarDuration.Short
        override var onDismiss: (() -> Unit)? = null
        override var onAction: (() -> Unit)? = null
    }
}
