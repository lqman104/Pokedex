package com.luqman.pokedex.ui.mypokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.luqman.pokedex.R
import com.luqman.pokedex.core.model.asString
import com.luqman.pokedex.data.repository.model.MyPokemon
import com.luqman.pokedex.uikit.component.ImageComponent
import com.luqman.pokedex.uikit.theme.AppTheme

@Composable
fun MyPokemonListScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: MyPokemonListViewModel = hiltViewModel()
) {
    val state = viewModel.myPokemon.observeAsState().value
    val delete = viewModel.deleteState.collectAsState().value
    val context = LocalContext.current

    when {
        delete.errorMessage != null -> {
            ShowSnackbar(delete.errorMessage.asString(context), snackbarHostState) {
                viewModel.resetDeleteState()
            }
        }
        delete.success -> {
            ShowSnackbar(stringResource(id =R.string.success_release_message), snackbarHostState) {
                viewModel.resetDeleteState()
            }
        }
    }

    Surface {
        MyPokemonListScreen(
            modifier = modifier,
            list = state.orEmpty()
        ) {
            viewModel.release(it)
        }
    }
}

@Composable
fun ShowSnackbar(
    message: String,
    snackbarHostState: SnackbarHostState,
    onDismissed : () -> Unit
) {
    LaunchedEffect(Unit) {
        val result = snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Long,
            withDismissAction = true,
        )

        if (result == SnackbarResult.Dismissed) {
            onDismissed()
        }
    }
}

@Composable
private fun MyPokemonListScreen(
    modifier: Modifier = Modifier,
    list: List<MyPokemon>,
    onReleaseClicked: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(list) { item ->
            PokemonItem(pokemon = item) {
                // onclick the pokemon list item
                onReleaseClicked(item.id)
            }
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: MyPokemon,
    modifier: Modifier = Modifier,
    onReleaseClicked: (MyPokemon) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            ImageComponent(
                model = pokemon.image,
                modifier = Modifier
                    .widthIn(max = 100.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pokemon.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pokemon.customName,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Button(
                onClick = {
                    onReleaseClicked(pokemon)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.release_button)
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    AppTheme {
        Surface {
            MyPokemonListScreen(
                modifier = Modifier.fillMaxSize(),
                list = listOf(
                    MyPokemon(
                        0,
                        0,
                        "Test",
                        "Test",
                        "Test"
                    )
                ),
                onReleaseClicked = {}
            )
        }
    }
}