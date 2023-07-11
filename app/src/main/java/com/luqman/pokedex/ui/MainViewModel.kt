package com.luqman.pokedex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.luqman.pokedex.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    val response = getPokemonListUseCase.invoke().cachedIn(viewModelScope)
}