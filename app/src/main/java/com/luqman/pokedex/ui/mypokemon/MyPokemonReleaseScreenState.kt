package com.luqman.pokedex.ui.mypokemon

import com.luqman.pokedex.core.model.UiText

data class MyPokemonReleaseScreenState(
    val success: Boolean = false,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)