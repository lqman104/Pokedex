package com.luqman.pokedex.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luqman.pokedex.data.repository.model.Pokemon

class PokemonPagingDataSource(
    private val dataSource: PokemonDataSource,
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize
            val offset = page * limit
            val data = dataSource.fetch(offset = offset, limit = limit)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }
}