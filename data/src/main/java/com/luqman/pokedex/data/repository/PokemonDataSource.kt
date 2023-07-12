package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail

interface PokemonDataSource {
    suspend fun fetch(offset: Int, limit: Int): List<Pokemon>
    suspend fun get(name: String): PokemonDetail
    suspend fun getAll(): List<Pokemon>
    suspend fun catch(pokemon: PokemonDetail, name: String)
    suspend fun release(id: Int)
}