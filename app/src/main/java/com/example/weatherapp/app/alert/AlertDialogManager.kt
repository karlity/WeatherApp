package com.example.weatherapp.app.alert

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AlertDialogManager {

    private val _command = MutableSharedFlow<AlertDialogCommand>(extraBufferCapacity = 10)
    val commands: SharedFlow<AlertDialogCommand> = _command

    private val _toggleDialog = MutableSharedFlow<Boolean>(extraBufferCapacity = 10)
    val toggleDialog: SharedFlow<Boolean> = _toggleDialog

    fun showAlertDialog(
        showAlertDialog: AlertDialogCommand
    ) {
        _command.tryEmit(showAlertDialog)
    }

    fun dismissAlertDialog(
        toggleDialog: Boolean
    ) {
        _toggleDialog.tryEmit(toggleDialog)
    }
}
