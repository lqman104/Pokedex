package com.luqman.pokedex.uikit.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luqman.pokedex.uikit.R
import com.luqman.pokedex.uikit.theme.AppTheme


@Composable
fun ErrorScreenComponent(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    actionButtonText: String = "",
    showActionButton: Boolean = false,
    onActionButtonClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_broken_egg),
            contentDescription = null,
            modifier = Modifier
                .widthIn(max = 200.dp, min = 180.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.FillHeight,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp)
        )

        if (showActionButton)
            Spacer(modifier = Modifier.padding(12.dp))
        Button(onClick = { onActionButtonClicked() }) {
            Text(text = actionButtonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenComponentPreview() {
    AppTheme {
        ErrorScreenComponent(
            message = "Try again later",
            title = "No internet connection",
            showActionButton = true,
            actionButtonText = "Retry",
            modifier = Modifier.fillMaxSize()
        )
    }
}