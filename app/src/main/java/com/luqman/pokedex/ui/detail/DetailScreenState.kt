package com.luqman.pokedex.ui.detail

import com.luqman.pokedex.core.model.UiText
import com.luqman.pokedex.data.repository.model.PokemonDetail

data class DetailScreenState(
    val data: PokemonDetail? = null,
    val loading: Boolean = true,
    val errorMessage: UiText? = null
)
