package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.toPokemon
import com.luqman.pokedex.data.services.PokemonService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PokemonRemoteDataSource(
    private val pokemonService: PokemonService,
    private val dispatcher: CoroutineDispatcher
) : PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        return withContext(dispatcher){
            pokemonService.get(offset, limit).results?.map {
                it.toPokemon()
            } ?: emptyList()
        }
    }

}