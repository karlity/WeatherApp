package com.example.weatherapp.app.snackbar

import androidx.compose.material.SnackbarDuration
import androidx.compose.ui.graphics.Color

interface SnackbarCommand {

    val messageResId: Int

    val actionButtonTextResId: Int?

    val duration: SnackbarDuration

    val color: Color

    var onDismiss: (() -> Unit)?

    var onAction: (() -> Unit)?
}
