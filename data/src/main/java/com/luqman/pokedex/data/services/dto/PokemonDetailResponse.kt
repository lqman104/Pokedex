package com.luqman.pokedex.data.services.dto

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(

	@Json(name="location_area_encounters")
	val locationAreaEncounters: String? = null,

	@Json(name="types")
	val types: List<TypesItem>? = null,

	@Json(name="base_experience")
	val baseExperience: Int? = null,

	@Json(name="weight")
	val weight: Int,

	@Json(name="is_default")
	val isDefault: Boolean? = null,

	@Json(name="sprites")
	val sprites: Sprites? = null,

	@Json(name="abilities")
	val abilities: List<AbilitiesItem>? = null,

	@Json(name="species")
	val species: Species? = null,

	@Json(name="stats")
	val stats: List<StatsItem>? = null,

	@Json(name="moves")
	val moves: List<MovesItem>? = null,

	@Json(name="name")
	val name: String,

	@Json(name="id")
	val id: Int,

	@Json(name="forms")
	val forms: List<FormsItem>? = null,

	@Json(name="height")
	val height: Int,

	@Json(name="order")
	val order: Int
)


@JsonClass(generateAdapter = true)
data class FormsItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="url")
	val url: String? = null
)

@JsonClass(generateAdapter = true)
data class AbilitiesItem(

	@Json(name="is_hidden")
	val isHidden: Boolean? = null,

	@Json(name="ability")
	val ability: Ability? = null,

	@Json(name="slot")
	val slot: Int? = null
)

@JsonClass(generateAdapter = true)
data class Home(

	@Json(name="front_shiny_female")
	val frontShinyFemale: Any? = null,

	@Json(name="front_default")
	val frontDefault: String? = null,

	@Json(name="front_female")
	val frontFemale: Any? = null,

	@Json(name="front_shiny")
	val frontShiny: String? = null
)

@JsonClass(generateAdapter = true)
data class Species(

	@Json(name="name")
	val name: String? = null,

	@Json(name="url")
	val url: String? = null
)

@JsonClass(generateAdapter = true)
data class StatsItem(

	@Json(name="stat")
	val stat: Stat? = null,

	@Json(name="base_stat")
	val baseStat: Int? = null,

	@Json(name="effort")
	val effort: Int? = null
)

@JsonClass(generateAdapter = true)
data class MovesItem(

	@Json(name="move")
	val move: Move? = null
)

@JsonClass(generateAdapter = true)
data class DreamWorld(

	@Json(name="front_default")
	val frontDefault: String? = null,

	@Json(name="front_female")
	val frontFemale: Any? = null
)

@JsonClass(generateAdapter = true)
data class Other(

	@Json(name="dream_world")
	val dreamWorld: DreamWorld? = null,

	@Json(name="home")
	val home: Home? = null
)

@JsonClass(generateAdapter = true)
data class Sprites(

	@Json(name="back_shiny_female")
	val backShinyFemale: Any? = null,

	@Json(name="back_female")
	val backFemale: Any? = null,

	@Json(name="other")
	val other: Other? = null,

	@Json(name="back_default")
	val backDefault: String? = null,

	@Json(name="front_shiny_female")
	val frontShinyFemale: Any? = null,

	@Json(name="front_default")
	val frontDefault: String? = null,

	@Json(name="front_female")
	val frontFemale: Any? = null,

	@Json(name="back_shiny")
	val backShiny: String? = null,

	@Json(name="front_shiny")
	val frontShiny: String? = null
)

@JsonClass(generateAdapter = true)
data class Stat(

	@Json(name="name")
	val name: String? = null,

	@Json(name="url")
	val url: String? = null
)

@JsonClass(generateAdapter = true)
data class Type(

	@Json(name="name")
	val name: String? = null,

	@Json(name="url")
	val url: String? = null
)

@JsonClass(generateAdapter = true)
data class TypesItem(

	@Json(name="slot")
	val slot: Int? = null,

	@Json(name="type")
	val type: Type? = null
)

@JsonClass(generateAdapter = true)
data class Move(

	@Json(name="name")
	val name: String? = null,

	@Json(name="url")
	val url: String? = null
)

@JsonClass(generateAdapter = true)
data class Ability(

	@Json(name="name")
	val name: String? = null,

	@Json(name="url")
	val url: String? = null
)