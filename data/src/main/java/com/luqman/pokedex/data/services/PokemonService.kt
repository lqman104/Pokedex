package com.luqman.pokedex.data.services

import com.luqman.pokedex.data.services.dto.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val POKEMON_ENDPOINT = "pokemon"
private const val LIMIT_QUERY = "limit"
private const val OFFSET_QUERY = "offset"

interface PokemonService {
    @GET(POKEMON_ENDPOINT)
    suspend fun get(
        @Query(OFFSET_QUERY) offset: Int,
        @Query(LIMIT_QUERY) limiter: Int
    ): PokemonResponse
}