package com.luqman.template.data.repository

import com.luqman.template.data.repository.model.Response
import com.luqman.template.data.services.SomeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteDataSource(
    private val someService: SomeService,
    private val dispatcher: CoroutineDispatcher
) : DataSource {

    override suspend fun fetch(): List<Response> {
        // TODO:: change with the real data
        return withContext(dispatcher) {
            someService.fetch().map {
                Response(1, it)
            }
        }
    }
}