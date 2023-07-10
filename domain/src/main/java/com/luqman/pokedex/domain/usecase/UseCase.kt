package com.luqman.pokedex.domain.usecase

import com.luqman.pokedex.core.model.UiText
import com.luqman.pokedex.core.model.ValidationResult
import com.luqman.pokedex.domain.R

class UseCase {

    operator fun invoke(input: String): ValidationResult {
        return when {
            input.isEmpty() -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.data_empty_error)
            )
            else -> {
                ValidationResult(
                    successful = true
                )
            }
        }
    }
}