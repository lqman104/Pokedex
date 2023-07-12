package com.luqman.pokedex.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.PokemonPagingDataSource
import com.luqman.pokedex.data.repository.model.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
) {

    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return Pager(
            pagingSourceFactory = { PokemonPagingDataSource(pokemonDataSource) },
            config = PagingConfig(pageSize = KEY_LIMIT)
        ).flow
    }

    companion object {
        private const val KEY_LIMIT = 20
    }
}