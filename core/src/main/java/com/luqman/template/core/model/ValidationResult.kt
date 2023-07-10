package com.luqman.template.core.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)