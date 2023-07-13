package com.luqman.pokedex.domain.usecase

import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.model.MyPokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyPokemonListUseCase @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
) {

    suspend operator fun invoke(): Flow<List<MyPokemon>> {
        return pokemonDataSource.getAll()
    }
}