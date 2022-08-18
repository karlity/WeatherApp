package com.example.weatherapp.app.alert

import com.example.weatherapp.R

data class AlertDialogCommand(
    val showDialog: Boolean,
    val title: Int? = null,
    val message: Int? = null,
    val positiveActionText: Int? = R.string.weather,
    val onPositiveAction: (() -> Unit)? = null,
    val negativeActionText: Int? = R.string.app_name,
    val onNegativeAction: (() -> Unit)? = null,
    var onDismiss: (() -> Unit)? = null,
    var onAction: (() -> Unit)? = null,
)
