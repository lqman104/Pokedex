package com.luqman.pokedex.domain.usecase

import com.luqman.pokedex.core.model.Resource
import com.luqman.pokedex.core.model.toUiText
import com.luqman.pokedex.core.network.exception.ApiException
import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.model.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
) {
    operator fun invoke(name: String): Flow<Resource<PokemonDetail>> = flow {
        try {
            emit(Resource.Loading())
            val response = pokemonDataSource.get(name)
            emit(Resource.Success(response))
        } catch (e: ApiException) {
            emit(Resource.Error(e.titleMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.toUiText()))
        }
    }
}