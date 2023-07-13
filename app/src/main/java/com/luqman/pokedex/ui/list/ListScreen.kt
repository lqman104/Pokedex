package com.luqman.pokedex.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.luqman.pokedex.R
import com.luqman.pokedex.core.model.asString
import com.luqman.pokedex.core.network.exception.ApiException
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.uikit.component.ErrorScreenComponent
import com.luqman.pokedex.uikit.component.ImageComponent
import com.luqman.pokedex.uikit.component.LoadingComponent
import com.luqman.pokedex.uikit.component.LoadingItemComponent
import com.luqman.pokedex.uikit.theme.AppTheme
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: ListViewModel = hiltViewModel(),
    onItemClicked: (String) -> Unit
) {
    val lazyPagingItems = viewModel.response.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val actionButton = stringResource(id = R.string.retry_button)

    ListScreen(
        modifier = modifier,
        list = lazyPagingItems,
        onItemClicked = onItemClicked,
        onFailedGetNextPage = {
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    it,
                    duration = SnackbarDuration.Long,
                    actionLabel = actionButton
                )
                if (result == SnackbarResult.ActionPerformed) {
                    lazyPagingItems.retry()
                }
            }
        },
    )
}

@Composable
private fun ListScreen(
    modifier: Modifier = Modifier,
    list: LazyPagingItems<Pokemon>,
    onFailedGetNextPage: (String) -> Unit,
    onItemClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(CELL_COUNT),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        handleFirstState(list.loadState.prepend) {
            list.retry()
        }

        handleFirstState(list.loadState.refresh) {
            list.retry()
        }

        items(count = list.itemCount) { index ->
            val item = list[index]
            GridItem(pokemon = item) {
                // onclick the pokemon list item
                onItemClicked(it?.name.orEmpty())
            }
        }

        handleAppendState(list.loadState.append) {
            onFailedGetNextPage(it)
        }
    }
}

@Composable
fun GridItem(
    pokemon: Pokemon?,
    modifier: Modifier = Modifier,
    onClickItem: (Pokemon?) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClickItem(pokemon)
            },
    ) {
        ImageComponent(
            model = pokemon?.image,
            modifier = Modifier
                .widthIn(max = 100.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
                .padding(4.dp),
        )
        Text(
            text = pokemon?.name.orEmpty(),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

private fun LazyGridScope.handleAppendState(state: LoadState, onError: (String) -> Unit) {
    item(
        span = {
            GridItemSpan(CELL_COUNT)
        }
    ) {
        when (state) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> LoadingItemComponent()
            is LoadState.Error -> onError(state.getErrorMessage())
        }
    }
}

private fun LazyGridScope.handleFirstState(state: LoadState, onRetryClicked: () -> Unit) {
    item(
        span = {
            GridItemSpan(CELL_COUNT)
        }
    ) {
        val modifier = Modifier
            .fillMaxSize()
            .height(300.dp)

        when (state) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> LoadingComponent(modifier)
            is LoadState.Error -> ErrorScreenComponent(
                showActionButton = true,
                actionButtonText = stringResource(id = R.string.retry_button),
                modifier = modifier,
                title = state.getTitleMessage(),
                message = state.getErrorMessage()
            ) {
                onRetryClicked()
            }
        }
    }
}

@Composable
private fun LoadState.Error.getErrorMessage(): String {
    return when (this.error) {
        is ApiException -> (this.error as ApiException).errorMessage.asString()
        else -> this.error.message.orEmpty()
    }
}

@Composable
private fun LoadState.Error.getTitleMessage(): String {
    return when (this.error) {
        is ApiException -> (this.error as ApiException).titleMessage.asString()
        else -> stringResource(id = com.luqman.pokedex.core.R.string.server_error_exception)
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    AppTheme {
        Surface {
            ListScreen(
                modifier = Modifier.fillMaxSize(),
                list = flowOf(
                    PagingData.from(
                        listOf(
                            Pokemon(
                                0,
                                "Test",
                                "Test"
                            )
                        )
                    )
                ).collectAsLazyPagingItems(),
                onItemClicked = {},
                onFailedGetNextPage = {}
            )
        }
    }
}

private const val CELL_COUNT = 2