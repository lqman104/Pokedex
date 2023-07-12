package com.luqman.pokedex.data.repository.model

import androidx.annotation.StringRes
import com.luqman.pokedex.data.R
import com.luqman.pokedex.data.services.dto.PokemonDetailResponse

data class PokemonDetail(
    val id: Int,
    val name: String,
    val customName: String? = null,
    val baseExperience: Int? = null,
    val height: Int,
    val weight: Int,
    val moves: List<String>,
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<PokemonStat>,
    val imageUrl: String,
) {
    val url: String = "https://pokeapi.co/api/v2/pokemon/${id}"
    val summaries: List<Summary> = listOf(
        Summary(
            title = R.string.weight,
            value = "${weight}Kg"
        ),
        Summary(
            title = R.string.height,
            value = "${weight}m"
        ),
        Summary(
            title = R.string.base_exp,
            value = "$baseExperience"
        )
    )

}

data class Summary(
    @StringRes val title: Int,
    val value: String
)

data class PokemonStat(
    val name: String,
    val value: Int
)


fun PokemonDetailResponse.toPokemonDetail(): PokemonDetail {
    val image =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id}.png"

    return PokemonDetail(
        id = id,
        name = name,
        baseExperience = baseExperience,
        height = height,
        weight = weight,
        moves = moves?.map {
            it.move?.name.orEmpty()
        }.orEmpty(),
        abilities = abilities?.map {
            it.ability?.name.orEmpty()
        }.orEmpty(),
        stats = stats?.map {
            PokemonStat(
                name = it.stat?.name.orEmpty(),
                value = it.baseStat ?: 0
            )
        }.orEmpty(),
        types = types?.map {
            it.type?.name.orEmpty()
        }.orEmpty(),
        imageUrl = image
    )
}
