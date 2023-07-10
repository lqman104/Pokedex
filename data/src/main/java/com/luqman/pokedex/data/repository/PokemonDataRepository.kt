package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.PokemonResponse

class PokemonDataRepository(
    private val localPokemonDataSource: PokemonDataSource,
    private val remotePokemonDataSource: PokemonDataSource
): PokemonDataSource {

    override suspend fun fetch(offside: Int, limit: Int): List<PokemonResponse> {
        return remotePokemonDataSource.fetch(offside, limit)
    }
}