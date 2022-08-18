package com.example.weatherapp.app.snackbar

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class SnackbarManager {

    private val _command = MutableSharedFlow<SnackbarCommand>(extraBufferCapacity = 10)
    val commands: SharedFlow<SnackbarCommand> = _command

    fun showSnackbar(
        snackBarData: SnackbarCommand
    ) {
        _command.tryEmit(snackBarData)
    }
}
