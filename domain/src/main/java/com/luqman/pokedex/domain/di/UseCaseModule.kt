package com.luqman.pokedex.domain.di

import com.luqman.pokedex.data.repository.PokemonDataSource
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

}