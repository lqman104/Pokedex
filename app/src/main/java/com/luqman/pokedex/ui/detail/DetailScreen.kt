package com.luqman.pokedex.ui.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.luqman.pokedex.R
import com.luqman.pokedex.core.helper.MyHelper.titleCase
import com.luqman.pokedex.core.model.UiText
import com.luqman.pokedex.core.model.asString
import com.luqman.pokedex.data.repository.model.PokemonDetail
import com.luqman.pokedex.data.repository.model.PokemonStat
import com.luqman.pokedex.data.repository.model.Summary
import com.luqman.pokedex.ui.catchdialog.CaughtAlertDialogComponent
import com.luqman.pokedex.ui.catchdialog.NotCaughtAlertDialogComponent
import com.luqman.pokedex.uikit.component.ErrorScreenComponent
import com.luqman.pokedex.uikit.component.ImageComponent
import com.luqman.pokedex.uikit.component.LoadingComponent
import com.luqman.pokedex.uikit.theme.AppTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    var canShowCatchButton by remember {
        mutableStateOf(false)
    }
    val detailState = viewModel.detailPokemon.collectAsState().value
    val isCaughtState = viewModel.catchState.collectAsState().value
    val storeState = viewModel.storeState.collectAsState().value
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    if (isCaughtState != null) {
        if (isCaughtState) {
            CaughtAlertDialogComponent(
                onDismissClicked = {
                    viewModel.release()
                },
                onSaveClicked = {
                    viewModel.store(it)
                }
            )
        } else {
            NotCaughtAlertDialogComponent()
        }
    }

    when {
        storeState.loading -> LoadingComponent(modifier.fillMaxSize())
        storeState.errorMessage != null -> {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    storeState.errorMessage.asString(context),
                    duration = SnackbarDuration.Long
                )
            }
        }

        storeState.success -> {
            navHostController.navigateUp()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TobBarDetail { navHostController.navigateUp() }
        },
        bottomBar = {
            if (canShowCatchButton) {
                ButtonCatch(modifier = Modifier.padding(16.dp), onClick = { viewModel.catch() })
            }
        }
    ) { paddingValues ->
        Surface(modifier = modifier.padding(paddingValues)) {
            when {
                detailState.loading -> LoadingComponent(modifier.fillMaxSize())
                detailState.errorMessage != null -> PokemonErrorScreen(detailState.errorMessage) {
                    viewModel.retry()
                }

                else -> {
                    detailState.data?.let {
                        canShowCatchButton = true
                        PokemonDetailComponent(
                            pokemonDetail = it,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonErrorScreen(
    errorMessage: UiText,
    onRetryPressed: () -> Unit
) {
    ErrorScreenComponent(
        title = errorMessage.asString(),
        showActionButton = true,
        actionButtonText = stringResource(id = R.string.retry_button),
        onActionButtonClicked = {
            // retry if the failed to get data
            onRetryPressed()
        }
    )
}

@Composable
fun PokemonDetailComponent(
    pokemonDetail: PokemonDetail,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageComponent(
            model = pokemonDetail.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(250.dp)
                .align(Alignment.CenterHorizontally),
        )

        val customName = pokemonDetail.customName
        if (!customName.isNullOrEmpty()) {
            Text(
                text = customName.titleCase(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Text(
            text = pokemonDetail.name.titleCase(),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        TypeRow(pokemonDetail.types)

        Spacer(modifier = Modifier.padding(8.dp))

        SummaryRow(pokemonDetail.summaries)

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = stringResource(id = R.string.stats),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(4.dp))

        StatColumn(stats = pokemonDetail.stats)

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = stringResource(id = R.string.abilities),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(4.dp))

        AbilitiesColumn(abilities = pokemonDetail.abilities)
    }
}

@Composable
fun TypeRow(
    listType: List<String>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        listType.forEach {
            TypeItem(label = it.titleCase())
        }
    }
}

@Composable
fun TypeItem(
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = modifier.padding(horizontal = 4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = label,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
fun SummaryRow(
    summaries: List<Summary>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        summaries.forEach {
            SummaryRowItem(
                title = stringResource(id = it.title),
                value = it.value
            )
        }
    }
}

@Composable
fun SummaryRowItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Surface(
            shadowElevation = 3.dp,
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
                .height(80.dp)
                .width(80.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}

@Composable
fun StatColumn(
    stats: List<PokemonStat>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        stats.forEach {
            StatItem(
                title = it.name,
                value = it.value,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun StatItem(
    title: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    val animateProgress by animateFloatAsState(targetValue = value.toFloat() / 100f)

    Row(
        modifier = modifier
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Text(
            modifier = modifier
                .weight(1f)
                .padding(end = 12.dp),
            textAlign = TextAlign.End,
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        LinearProgressIndicator(progress = animateProgress)
    }
}

@Composable
fun AbilitiesColumn(
    abilities: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        abilities.forEach {
            AbilityItem(
                value = it,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AbilityItem(
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = value.titleCase(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TobBarDetail(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(
                modifier = Modifier.clip(CircleShape),
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.button_back)
                )
            }

        }
    )
}

@Composable
fun ButtonCatch(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClick()
        }
    ) {
        Text(text = stringResource(id = R.string.catch_button))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    AppTheme {
        PokemonDetailComponent(
            pokemonDetail = PokemonDetail(
                id = 1,
                name = "test",
                customName = "testing",
                baseExperience = 123,
                height = 123,
                weight = 123,
                moves = listOf(),
                abilities = listOf("Run", "Hide"),
                stats = listOf(PokemonStat("Attack", 50), PokemonStat("Defense", 50)),
                imageUrl = "",
                types = listOf("water", "grass")
            )
        )
    }
}