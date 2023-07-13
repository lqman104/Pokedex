package com.luqman.pokedex.ui.mypokemon

import com.luqman.pokedex.core.model.UiText
import com.luqman.pokedex.data.repository.model.MyPokemon
import com.luqman.pokedex.data.repository.model.Pokemon
import com.luqman.pokedex.data.repository.model.PokemonDetail

data class MyPokemonListScreenState(
    val data: List<MyPokemon>? = null,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)