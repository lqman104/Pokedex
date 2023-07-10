package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.Pokemon

class PokemonDataRepository(
    private val localPokemonDataSource: PokemonDataSource,
    private val remotePokemonDataSource: PokemonDataSource
): PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        return remotePokemonDataSource.fetch(offset, limit)
    }
}