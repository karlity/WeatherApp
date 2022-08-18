package com.example.weatherapp.app.snackbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SnackbarHandler(
    snackbarHostState: SnackbarHostState,
    scaffoldState: ScaffoldState,
    snackbarManager: SnackbarManager,
) {

    var color by remember { mutableStateOf<Color>(Color.LightGray) }

    val context = LocalContext.current
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { snackbarData: SnackbarData ->
            Snackbar(
                backgroundColor = color,
                action = {
                    snackbarData.actionLabel?.let { label ->
                        TextButton(onClick = { snackbarData.performAction() }) {
                            Text(
                                text = label,
                                fontSize = 16.sp
                            )
                        }
                    }
                },
                modifier = Modifier
                    .padding(40.dp)
                    .height(70.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = snackbarData.message,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }

    )
    LaunchedEffect(Unit) {

        snackbarManager.commands.collect { command ->
            color = command.color

            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = context.resources.getString(command.messageResId),
                actionLabel = command.actionButtonTextResId?.let { context.resources.getString(it) },
                duration = command.duration,
            )

            when (result) {
                SnackbarResult.Dismissed -> {
                    command.onDismiss?.let { it() }
                }
                SnackbarResult.ActionPerformed -> {
                    command.onAction?.let { it() }
                }
            }
        }
    }
}
