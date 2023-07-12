package com.luqman.pokedex.ui.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.luqman.pokedex.R
import com.luqman.pokedex.core.model.asString
import com.luqman.pokedex.data.repository.model.PokemonDetail
import com.luqman.pokedex.data.repository.model.PokemonStat
import com.luqman.pokedex.data.repository.model.Summary
import com.luqman.pokedex.uikit.component.ErrorScreenComponent
import com.luqman.pokedex.uikit.component.ImageComponent
import com.luqman.pokedex.uikit.component.LoadingComponent
import com.luqman.pokedex.uikit.theme.AppTheme
import java.util.Locale

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = viewModel.detailPokemon.collectAsState()

    DetailScaffold(
        modifier = modifier,
        detailScreenState = state.value,
        onBackPressed = { navHostController.popBackStack() },
        onRetryPressed = { viewModel.retry() }
    )
}

@Composable
fun DetailScaffold(
    detailScreenState: DetailScreenState,
    onBackPressed: () -> Unit,
    onRetryPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TobBarDetail {
                onBackPressed()
            }
        }
    ) { paddingValues ->
        Surface(modifier = modifier.padding(paddingValues)) {
            when {
                detailScreenState.loading -> LoadingComponent(modifier.fillMaxSize())
                detailScreenState.errorMessage != null -> ErrorScreenComponent(
                    title = detailScreenState.errorMessage.asString(),
                    showActionButton = true,
                    actionButtonText = stringResource(id = R.string.retry_button),
                    onActionButtonClicked = {
                        // retry if the failed to get data
                        onRetryPressed()
                    }
                )

                else -> {
                    detailScreenState.data?.let {
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
fun PokemonDetailComponent(
    pokemonDetail: PokemonDetail,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState),
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
                .padding(horizontal = 16.dp)
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
                .padding(horizontal = 16.dp)
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
            Surface(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.padding(horizontal = 4.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = it.titleCase(),
                    style = MaterialTheme.typography.labelMedium,
                )
            }

        }
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
            .padding(horizontal = 16.dp)
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
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

private fun String.titleCase(): String {
    return this.lowercase()
        .replaceFirstChar { it.titlecase(Locale.getDefault()) }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TobBarDetail(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier.padding(horizontal = 16.dp),
        title = {},
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.button_back),
                modifier = Modifier.clickable {
                    onBackPressed()
                }
            )
        }
    )
}

@Preview
@Composable
fun DetailContentPreview() {
    AppTheme {
        DetailScaffold(
            onBackPressed = {},
            onRetryPressed = {},
            detailScreenState = DetailScreenState(
                loading = false,
                data = PokemonDetail(
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
        )
    }
}