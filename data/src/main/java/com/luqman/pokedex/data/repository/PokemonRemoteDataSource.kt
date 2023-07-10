package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.PokemonResponse
import com.luqman.pokedex.data.services.SomeService
import kotlinx.coroutines.CoroutineDispatcher

class PokemonRemoteDataSource(
    private val someService: SomeService,
    private val dispatcher: CoroutineDispatcher
) : PokemonDataSource {

    override suspend fun fetch(offside: Int, limit: Int): List<PokemonResponse> {
        return listOf()
    }

}