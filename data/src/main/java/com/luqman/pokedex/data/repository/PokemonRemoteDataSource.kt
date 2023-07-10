package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.toPokemon
import com.luqman.pokedex.data.services.PokemonService

class PokemonRemoteDataSource(
    private val pokemonService: PokemonService
) : PokemonDataSource {

    override suspend fun fetch(offset: Int, limit: Int): List<Pokemon> {
        return pokemonService.get(offset, limit).data.results?.map {
            it.toPokemon()
        } ?: emptyList()
    }

}