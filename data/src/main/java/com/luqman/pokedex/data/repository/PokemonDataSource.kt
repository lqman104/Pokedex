package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.MyPokemon
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    suspend fun fetch(offset: Int, limit: Int): List<Pokemon>
    suspend fun get(name: String): PokemonDetail
    suspend fun getAll(): Flow<List<MyPokemon>>
    suspend fun catch(pokemon: PokemonDetail, name: String)
    suspend fun release(id: Int)
}