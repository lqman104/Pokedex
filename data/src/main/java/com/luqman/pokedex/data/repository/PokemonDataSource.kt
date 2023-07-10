package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.Pokemon

interface PokemonDataSource {
    suspend fun fetch(offset: Int, limit: Int): List<Pokemon>
}