package com.luqman.pokedex.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luqman.pokedex.data.repository.model.PokemonResponse

class PokemonPagingDataSource(
    private val dataSource: PokemonDataSource,
) : PagingSource<Int, PokemonResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResponse> {
        return try {
            try {
                val page = params.key ?: 0
                val size = params.loadSize
                val offside = page * size
                val data = dataSource.fetch(offside = offside, KEY_LIMIT)
                LoadResult.Page(
                    data = listOf(),
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResponse>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val KEY_LIMIT = 20
    }
}