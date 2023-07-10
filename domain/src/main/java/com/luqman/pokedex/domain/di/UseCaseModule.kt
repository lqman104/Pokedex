package com.luqman.pokedex.domain.di

import com.luqman.pokedex.domain.usecase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(): UseCase {
        return UseCase()
    }

}