package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.database.entity.MyPokemonEntity
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail

class PokemonDataRepository(
    private val localPokemonDataSource: PokemonDataSource,
    private val remotePokemonDataSource: PokemonDataSource
): PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        return remotePokemonDataSource.fetch(offset, limit)
    }

    override suspend fun get(name: String): PokemonDetail {
        return remotePokemonDataSource.get(name)
    }

    override suspend fun getAll(): List<Pokemon> {
        return localPokemonDataSource.getAll()
    }

    override suspend fun catch(pokemon: Pokemon, name: String) {
        localPokemonDataSource.catch(pokemon, name)
    }

    override suspend fun release(id: Int) {
        localPokemonDataSource.release(id)
    }
}