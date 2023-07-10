package com.luqman.pokedex.data.di

import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.PokemonLocalDataSource
import com.luqman.pokedex.data.repository.PokemonDataRepository
import com.luqman.pokedex.data.repository.PokemonRemoteDataSource
import com.luqman.pokedex.data.services.SomeService
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
    ): SomeService = retrofit.create(SomeService::class.java)

    @Provides
    @LocalSource
    fun provideProvinceLocalDataSource(): PokemonDataSource = PokemonLocalDataSource(
        Dispatchers.IO
    )

    @Provides
    @RemoteSource
    fun provideProvinceRemoteDataSource(
        someService: SomeService
    ): PokemonDataSource = PokemonRemoteDataSource(
        someService,
        Dispatchers.IO
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