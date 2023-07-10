package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.Pokemon

class PokemonLocalDataSource : PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        return emptyList()
    }
}