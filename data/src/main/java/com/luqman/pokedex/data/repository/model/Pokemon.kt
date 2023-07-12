package com.luqman.pokedex.data.repository.model

import com.luqman.pokedex.data.services.dto.PokemonResultsItem

data class Pokemon(
    val id: Int,
    val url: String,
    val name: String,
    val customName: String? = null
) {
    val image: String
        get() {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id}.png"
        }
}

fun PokemonResultsItem.toPokemon(): Pokemon {
    val id: Int = url?.split("/")?.dropLast(1)?.last()?.toIntOrNull() ?: 0

    return Pokemon(
        id = id,
        name = name.orEmpty(),
        url = url.orEmpty()
    )
}