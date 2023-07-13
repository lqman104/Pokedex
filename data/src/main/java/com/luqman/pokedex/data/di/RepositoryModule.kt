package com.luqman.pokedex.data.di

import android.content.Context
import androidx.room.Room
import com.luqman.pokedex.data.database.PokemonDatabase
import com.luqman.pokedex.data.database.dao.MyPokemonDao
import com.luqman.pokedex.data.repository.PokemonDataRepository
import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.PokemonLocalDataSource
import com.luqman.pokedex.data.repository.PokemonRemoteDataSource
import com.luqman.pokedex.data.services.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMyPokemonDatabase(
        @ApplicationContext context: Context
    ): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "PokemonDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMyPokemonDao(
        database: PokemonDatabase
    ): MyPokemonDao {
        return database.myPokemonDao()
    }

    @Provides
    fun provideProvinceApiService(
        retrofit: Retrofit
    ): PokemonService = retrofit.create(PokemonService::class.java)

    @Provides
    @LocalSource
    fun provideProvinceLocalDataSource(
        pokemonDao: MyPokemonDao
    ): PokemonDataSource = PokemonLocalDataSource(
        pokemonDao,
        Dispatchers.IO
    )

    @Provides
    @RemoteSource
    fun provideProvinceRemoteDataSource(
        pokemonService: PokemonService
    ): PokemonDataSource = PokemonRemoteDataSource(
        pokemonService,
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