package com.luqman.pokedex.data.repository

import com.luqman.pokedex.core.exception.ImplementationShouldNotCalledException
import com.luqman.pokedex.data.repository.model.MyPokemon
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail
import com.luqman.pokedex.data.repository.model.toPokemon
import com.luqman.pokedex.data.repository.model.toPokemonDetail
import com.luqman.pokedex.data.services.PokemonService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PokemonRemoteDataSource(
    private val pokemonService: PokemonService,
    private val dispatcher: CoroutineDispatcher
) : PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        return withContext(dispatcher) {
            pokemonService.getAll(offset, limit).results?.map {
                it.toPokemon()
            } ?: emptyList()
        }
    }

    override suspend fun get(name: String): PokemonDetail {
        return withContext(dispatcher) {
            val response = pokemonService.get(name)
            response.toPokemonDetail()
        }
    }

    override suspend fun getAll(): Flow<List<MyPokemon>> {
        throw ImplementationShouldNotCalledException()
    }

    override suspend fun catch(pokemon: PokemonDetail, name: String) {
        throw ImplementationShouldNotCalledException()

    }

    override suspend fun release(id: Int) {
        throw ImplementationShouldNotCalledException()
    }
}