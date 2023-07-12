package com.luqman.pokedex.uikit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.luqman.pokedex.uikit.R

@Composable
fun ImageComponent(
    model: Any?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = model,
        contentDescription = null,
        placeholder = painterResource(R.drawable.ic_img),
        modifier = modifier
    )
}