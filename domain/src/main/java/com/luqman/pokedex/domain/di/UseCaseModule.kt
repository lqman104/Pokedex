package com.luqman.pokedex.domain.di

import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.domain.usecase.GetMyPokemonListUseCase
import com.luqman.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.luqman.pokedex.domain.usecase.GetPokemonListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPokemonListUseCase(
        repository: PokemonDataSource
    ): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }

    @Provides
    fun provideGetPokemonDetailUseCase(
        repository: PokemonDataSource
    ): GetPokemonDetailUseCase {
        return GetPokemonDetailUseCase(repository)
    }

    @Provides
    fun provideGetMyPokemonUseCase(
        repository: PokemonDataSource
    ): GetMyPokemonListUseCase {
        return GetMyPokemonListUseCase(repository)
    }

}