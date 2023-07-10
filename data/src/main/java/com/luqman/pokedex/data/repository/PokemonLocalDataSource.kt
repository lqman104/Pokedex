package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.PokemonResponse
import kotlinx.coroutines.CoroutineDispatcher

class PokemonLocalDataSource(
    private val dispatcher: CoroutineDispatcher
) : PokemonDataSource {

    override suspend fun fetch(offside: Int, limit: Int): List<PokemonResponse> {
        return emptyList()
    }
}