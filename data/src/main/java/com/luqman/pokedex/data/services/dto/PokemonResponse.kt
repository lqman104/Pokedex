package com.luqman.pokedex.data.services.dto

import com.luqman.pokedex.core.network.model.BaseResponse
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class PokemonResponse(

	@Json(name="next")
	val next: String? = null,

	@Json(name="previous")
	val previous: Any? = null,

	@Json(name="count")
	val count: Int? = null,

	@Json(name="results")
	val results: List<PokemonResultsItem>? = null
)

@JsonClass(generateAdapter = true)
data class PokemonResultsItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="url")
	val url: String? = null
)

typealias PokemonHttpResponse = BaseResponse<PokemonResponse>
