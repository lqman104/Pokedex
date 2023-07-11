package com.luqman.pokedex.data.services

import com.luqman.pokedex.data.services.dto.PokemonDetailResponse
import com.luqman.pokedex.data.services.dto.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val LIMIT_QUERY = "limit"
private const val OFFSET_QUERY = "offset"
private const val NAME_PATH = "name"

private const val POKEMON_ENDPOINT = "pokemon"
private const val POKEMON_DETAIL_ENDPOINT = "pokemon/{$NAME_PATH}"

interface PokemonService {
    @GET(POKEMON_ENDPOINT)
    suspend fun getAll(
        @Query(OFFSET_QUERY) offset: Int,
        @Query(LIMIT_QUERY) limiter: Int
    ): PokemonResponse

    @GET(POKEMON_DETAIL_ENDPOINT)
    suspend fun get(
        @Path(NAME_PATH) name: String
    ): PokemonDetailResponse
}