package com.luqman.pokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.luqman.pokedex.R
import com.luqman.pokedex.core.model.asString
import com.luqman.pokedex.core.network.exception.ApiException
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.uikit.component.ErrorScreenComponent
import com.luqman.pokedex.uikit.component.LoadingComponent
import com.luqman.pokedex.uikit.component.LoadingItemComponent
import com.luqman.pokedex.uikit.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel()
    val lazyPagingItems = viewModel.response.collectAsLazyPagingItems()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val actionButton = stringResource(id = R.string.retry_button)

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding: PaddingValues ->
        MainContent(
            list = lazyPagingItems,
            onFailedGetNextPage = { message ->
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message,
                        duration = SnackbarDuration.Long,
                        actionLabel = actionButton
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        lazyPagingItems.retry()
                    }
                }
            },
            modifier = Modifier
                .padding(padding),
        )
    }
}

@Composable
fun MainContent(
    list: LazyPagingItems<Pokemon>,
    onFailedGetNextPage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(CELL_COUNT),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        state = scrollState
    ) {
        handleFirstState(list.loadState.prepend) {
            list.retry()
        }

        handleFirstState(list.loadState.refresh) {
            list.retry()
        }

        items(count = list.itemCount) { index ->
            val item = list[index]
            Card(
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = item?.name.orEmpty(),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        handleAppendState(list.loadState.append) {
            onFailedGetNextPage(it)
        }
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
            .height(IntrinsicSize.Max)

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
fun SnackBarComponentPreview() {
    AppTheme {
        Surface {
            MainContent(
                modifier = Modifier.fillMaxSize(),
                list = flowOf(PagingData.from(emptyList<Pokemon>())).collectAsLazyPagingItems(),
                onFailedGetNextPage = {}
            )
        }
    }
}

private const val CELL_COUNT = 2