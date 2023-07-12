package com.luqman.pokedex.domain.usecase

import com.luqman.pokedex.core.model.Resource
import com.luqman.pokedex.core.model.toUiText
import com.luqman.pokedex.core.network.exception.ApiException
import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StorePokemonUseCase(
    private val pokemonDataSource: PokemonDataSource
) {
    operator fun invoke(
        pokemon: Pokemon,
        name: String
    ): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            pokemonDataSource.catch(pokemon, name)
            emit(Resource.Success(Any()))
        } catch (e: ApiException) {
            emit(Resource.Error(e.titleMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.toUiText()))
        }
    }
}