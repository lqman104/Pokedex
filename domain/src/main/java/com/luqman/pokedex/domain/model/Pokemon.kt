package com.luqman.pokedex.domain.model

import com.luqman.pokedex.data.repository.model.PokemonResponse

data class Pokemon(
    val id: Int,
    val url: String,
    val name: String,
)

fun PokemonResponse.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        url = url,
        name = name
    )
}