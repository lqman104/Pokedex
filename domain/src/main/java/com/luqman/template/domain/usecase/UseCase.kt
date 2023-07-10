package com.luqman.template.domain.usecase

import com.luqman.template.core.model.UiText
import com.luqman.template.core.model.ValidationResult
import com.luqman.template.domain.R

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