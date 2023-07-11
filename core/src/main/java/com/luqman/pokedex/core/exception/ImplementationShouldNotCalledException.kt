package com.luqman.pokedex.core.exception

class ImplementationShouldNotCalledException: Exception() {
    override val message: String
        get() = "Implementation Should Not Called"
}