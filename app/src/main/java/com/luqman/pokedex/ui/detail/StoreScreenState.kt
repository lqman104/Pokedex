package com.luqman.pokedex.ui.detail

import com.luqman.pokedex.core.model.UiText
import com.luqman.pokedex.data.repository.model.PokemonDetail

data class StoreScreenState(
    val success: Boolean = false,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)