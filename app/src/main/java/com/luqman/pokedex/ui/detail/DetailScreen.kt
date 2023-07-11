package com.luqman.pokedex.ui.detail

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val state = viewModel.detailPokemon.collectAsState()

    Surface(modifier = modifier) {
        if (state.value.data != null) {
            Text(text = state.value.data.toString())
        }
    }

}