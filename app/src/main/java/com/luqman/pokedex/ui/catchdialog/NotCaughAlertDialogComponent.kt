package com.luqman.pokedex.ui.catchdialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luqman.pokedex.R

@Composable
fun NotCaughtAlertDialogComponent(
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text(text = stringResource(id = R.string.not_caught_dialog_title))
            },
            text = {
                Text(
                    text = stringResource(id = R.string.not_caught_dialog_message),
                    color = MaterialTheme.colorScheme.error
                )
            },
            dismissButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text(stringResource(id = com.luqman.pokedex.uikit.R.string.ok_button))
                }
            },
            confirmButton = {}
        )
    }
}