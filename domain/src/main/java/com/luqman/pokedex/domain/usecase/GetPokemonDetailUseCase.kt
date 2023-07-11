package com.luqman.pokedex.domain.usecase

import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.model.PokemonDetail

class GetPokemonDetailUseCase(
    private val pokemonDataSource: PokemonDataSource
) {
    suspend fun invoke(name: String): PokemonDetail {
        return pokemonDataSource.get(name)
    }
}