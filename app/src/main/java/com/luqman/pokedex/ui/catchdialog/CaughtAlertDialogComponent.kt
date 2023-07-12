package com.luqman.pokedex.ui.catchdialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luqman.pokedex.R

@Composable
fun CaughtAlertDialogComponent(
    modifier: Modifier = Modifier,
    onDismissClicked: () -> Unit,
    onSaveClicked: (String) -> Unit,
) {
    var openDialog by remember { mutableStateOf(true) }
    var text by rememberSaveable{ mutableStateOf("") }
    val isValid by remember {
        derivedStateOf { text.isNotEmpty() }
    }

    if (openDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text(text = stringResource(id = R.string.pokemon_name_dialog_title))
            },
            text = {
                Column {
                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                        }
                    )
                    if (!isValid) {
                        Text(
                            text = stringResource(id = R.string.name_empty_error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValid,
                    onClick = {
                        openDialog = false
                        onSaveClicked(text)
                    }
                ) {
                    Text(stringResource(id = R.string.save_button))
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onDismissClicked()
                        openDialog = false
                    }
                ) {
                    Text(stringResource(id = R.string.dismiss_button))
                }
            }
        )
    }
}