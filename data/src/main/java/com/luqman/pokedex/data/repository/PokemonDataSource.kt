package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.PokemonResponse

interface PokemonDataSource {
    suspend fun fetch(offside: Int, limit: Int): List<PokemonResponse>
}