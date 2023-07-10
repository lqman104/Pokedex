package com.luqman.pokedex.data.repository

import com.luqman.pokedex.data.repository.model.Response

interface DataSource {
    suspend fun fetch(): List<Response>
}