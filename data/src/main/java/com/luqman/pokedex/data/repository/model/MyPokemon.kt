package com.luqman.pokedex.data.repository.model

import com.luqman.pokedex.data.database.entity.MyPokemonEntity

data class MyPokemon(
    val id: Int,
    val pokemonId: Int,
    val name: String,
    val customName: String,
    val url: String
) {
    val image: String
        get() {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${pokemonId}.png"
        }
}

fun MyPokemonEntity.toMyPokemon(): MyPokemon {
    return MyPokemon(
        id = id,
        pokemonId = pokemonId,
        name = name,
        url = url,
        customName = customName
    )
}