package com.luqman.pokedex.domain.usecase

import javax.inject.Inject

class CatchPokemonUseCase @Inject constructor() {
    operator fun invoke(): Boolean {
        val probability = listOf(true, false)
        return probability.random()
    }
}