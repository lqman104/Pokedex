package com.luqman.pokedex.core.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)