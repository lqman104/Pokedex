package com.luqman.pokedex.domain.usecase

import com.luqman.pokedex.core.model.Resource
import com.luqman.pokedex.core.model.toUiText
import com.luqman.pokedex.core.network.exception.ApiException
import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyPokemonListUseCase @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
) {

    operator fun invoke(): Flow<Resource<List<Pokemon>>> = flow {
        try {
            emit(Resource.Loading())
            val response = pokemonDataSource.getAll()
            emit(Resource.Success(response))
        } catch (e: ApiException) {
            emit(Resource.Error(e.titleMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.toUiText()))
        }
    }
}