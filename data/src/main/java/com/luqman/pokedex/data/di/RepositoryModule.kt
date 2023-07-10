package com.luqman.pokedex.data.di

import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.PokemonLocalDataSource
import com.luqman.pokedex.data.repository.PokemonDataRepository
import com.luqman.pokedex.data.repository.PokemonRemoteDataSource
import com.luqman.pokedex.data.services.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProvinceApiService(
        retrofit: Retrofit
    ): PokemonService = retrofit.create(PokemonService::class.java)

    @Provides
    @LocalSource
    fun provideProvinceLocalDataSource(): PokemonDataSource = PokemonLocalDataSource()

    @Provides
    @RemoteSource
    fun provideProvinceRemoteDataSource(
        pokemonService: PokemonService
    ): PokemonDataSource = PokemonRemoteDataSource(
        pokemonService
    )

    @Provides
    fun provideProvinceRepository(
        @RemoteSource remotePokemonDataSource: PokemonDataSource,
        @LocalSource localPokemonDataSource: PokemonDataSource
    ): PokemonDataSource = PokemonDataRepository(
        remotePokemonDataSource = remotePokemonDataSource,
        localPokemonDataSource = localPokemonDataSource
    )
}