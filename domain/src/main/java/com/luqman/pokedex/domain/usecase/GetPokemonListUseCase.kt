package com.luqman.pokedex.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.PokemonPagingDataSource
import com.luqman.pokedex.domain.model.Pokemon
import com.luqman.pokedex.domain.model.toPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonListUseCase(
    private val pokemonDataSource: PokemonDataSource
) {

    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return Pager(
            pagingSourceFactory = { PokemonPagingDataSource(pokemonDataSource) },
            config = PagingConfig(pageSize = 20)
        ).flow.map { list ->
            list.map { it.toPokemon() }
        }
    }
}