package com.example.weatherapp.app.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

@Composable
fun ExampleAlertDialog(
    showDialog: Boolean = false,
    title: String? = null,
    text: String? = null,
    positiveText: String? = stringResource(id = R.string.app_name),
    positiveAction: () -> Unit,
    negativeText: String? = stringResource(id = R.string.weather),
    negativeAction: () -> Unit,
    dismissAction: () -> Unit
) {

    if (showDialog) AlertDialog(
        onDismissRequest = {
            dismissAction()
        },
        title = {
            title?.let {
                Text(text = it, color = Color.Black)
            }
        },
        text = {
            text?.let {
                Text(text = it, color = Color.Black)
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(10.dp, 10.dp, 15.dp, 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = { negativeAction() },
                    modifier = Modifier.padding(5.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue)
                ) {
                    negativeText?.let { Text(it, fontSize = 16.sp) }
                }

                TextButton(
                    onClick = { positiveAction() },
                    modifier = Modifier.padding(5.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue)
                ) {
                    positiveText?.let { Text(it, fontSize = 16.sp) }
                }
            }
        }
    )
}
