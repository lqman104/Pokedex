package com.luqman.pokedex.data.repository

import com.luqman.pokedex.core.exception.ImplementationShouldNotCalledException
import com.luqman.pokedex.data.database.dao.MyPokemonDao
import com.luqman.pokedex.data.database.entity.MyPokemonEntity
import com.luqman.pokedex.data.repository.model.MyPokemon
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail
import com.luqman.pokedex.data.repository.model.toMyPokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonLocalDataSource(
    private val pokemonDao: MyPokemonDao,
    private val dispatcher: CoroutineDispatcher
) : PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        throw ImplementationShouldNotCalledException()
    }

    override suspend fun get(name: String): PokemonDetail {
        throw ImplementationShouldNotCalledException()
    }

    override suspend fun getAll(): Flow<List<MyPokemon>> {
        return withContext(context = dispatcher) {
            pokemonDao.getAll().map {
                it.map { pokemon ->
                    pokemon.toMyPokemon()
                }
            }
        }
    }

    override suspend fun catch(pokemon: PokemonDetail, name: String) {
        withContext(dispatcher) {
            val pokemonEntity = MyPokemonEntity(
                pokemonId = pokemon.id,
                name = pokemon.name,
                customName = name,
                url = pokemon.url
            )
            pokemonDao.insert(pokemonEntity)
        }
    }

    override suspend fun release(id: Int) {
        withContext(dispatcher) {
            pokemonDao.delete(id)
        }
    }
}