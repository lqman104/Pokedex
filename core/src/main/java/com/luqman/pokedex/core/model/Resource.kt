package com.luqman.pokedex.core.model

sealed class Resource<T>(
    val data: T? = null,
    val message: UiText? = null
) {
    data class Success<T>(
        val result: T?,
    ) : Resource<T>(data = result)

    data class Error<T>(
        val error: UiText?
    ) : Resource<T>(message = error)

    object Loading : Resource<Any>()
}
