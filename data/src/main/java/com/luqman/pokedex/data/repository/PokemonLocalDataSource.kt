package com.luqman.pokedex.data.repository

import com.luqman.pokedex.core.exception.ImplementationShouldNotCalledException
import com.luqman.pokedex.data.database.dao.MyPokemonDao
import com.luqman.pokedex.data.database.entity.MyPokemonEntity
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail
import kotlinx.coroutines.CoroutineDispatcher
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

    override suspend fun getAll(): List<Pokemon> {
        return withContext(dispatcher) {
            pokemonDao.getAll().map {
                Pokemon(
                    id = it.pokemonId,
                    name = it.name,
                    url = it.url
                )
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