package com.luqman.pokedex.data.repository.model

import com.luqman.pokedex.data.services.dto.PokemonResultsItem

data class Pokemon(
    val id: Int,
    val url: String,
    val name: String,
)

fun PokemonResultsItem.toPokemon(): Pokemon {
    val id: Int = url
        ?.replace("https://pokeapi.co/api/v2/pokemon/", "")
        ?.replace("/", "")
        ?.toIntOrNull() ?: 0

    return Pokemon(
        id = id,
        name = name.orEmpty(),
        url = url.orEmpty()
    )
}