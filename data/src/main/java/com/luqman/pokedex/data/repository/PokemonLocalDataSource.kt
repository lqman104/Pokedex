package com.luqman.pokedex.data.repository

import com.luqman.pokedex.core.exception.ImplementationShouldNotCalledException
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail

class PokemonLocalDataSource : PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        throw ImplementationShouldNotCalledException()
    }

    override suspend fun get(name: String): PokemonDetail {
        throw ImplementationShouldNotCalledException()
    }
}