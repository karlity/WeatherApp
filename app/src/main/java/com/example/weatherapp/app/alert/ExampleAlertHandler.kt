package com.example.weatherapp.app.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

@Composable
fun NiceAlertHandler(alertDialogManager: AlertDialogManager) {

    var alertData by remember { mutableStateOf(AlertDialogCommand(showDialog = false)) }

    LaunchedEffect(Unit) {
        alertDialogManager.commands.collect {
            alertData = it
        }
    }

    ExampleAlertDialog(
        showDialog = alertData.showDialog,
        title = alertData.title?.let { title -> stringResource(id = title) },
        text = alertData.message?.let { message -> stringResource(id = message) },
        positiveText = alertData.positiveActionText?.let { stringResource(id = it).uppercase() },
        positiveAction = {
            alertData.onPositiveAction?.let { it() }.also {
                alertDialogManager.showAlertDialog(
                    AlertDialogCommand(
                        false
                    )
                )
            }
        },
        negativeText = alertData.negativeActionText?.let { stringResource(id = it).uppercase() },
        negativeAction = {
            alertData.onNegativeAction?.let { it() }.also {
                alertDialogManager.showAlertDialog(
                    AlertDialogCommand(
                        false
                    )
                )
            }
        },
        dismissAction = {
            alertData.onDismiss?.let { it() }.also {
                alertDialogManager.showAlertDialog(
                    AlertDialogCommand(
                        false
                    )
                )
            }
        }
    )
}
