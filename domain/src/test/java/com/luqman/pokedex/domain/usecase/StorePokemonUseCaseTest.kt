package com.luqman.pokedex.domain.usecase

import com.luqman.pokedex.core.model.Resource
import com.luqman.pokedex.data.repository.PokemonDataSource
import com.luqman.pokedex.data.repository.model.PokemonDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class StorePokemonUseCaseTest {


    @Mock
    private lateinit var dataSource: PokemonDataSource
    private lateinit var useCase: StorePokemonUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = StorePokemonUseCase(dataSource)
    }

    @Test
    fun `given pokemon name when empty then return error`() = runTest {
        // given
        // when
        val firstEmit = useCase.invoke(pokemonDetail, "").first()

        // than
        assertEquals(firstEmit is Resource.Error, true)
    }

    @Test
    fun `given valid request when failed save return error`() = runTest {
        // given
        given(dataSource.catch(pokemonDetail, pokemonName)).willThrow(IllegalArgumentException())
        val list = useCase.invoke(pokemonDetail, pokemonName).toList()

        // than
        assertEquals(list[0] is Resource.Loading, true)
        assertEquals(list[1] is Resource.Error, true)
    }

    @Test
    fun `given valid request when success save return error`() = runTest {
        // given
        given(dataSource.catch(pokemonDetail, pokemonName)).willReturn(Unit)
        val list = useCase.invoke(pokemonDetail, pokemonName).toList()

        // than
        assertEquals(list[0] is Resource.Loading, true)
        assertEquals(list[1] is Resource.Success, true)
    }

    companion object {
        const val pokemonName = "MyPokemon"

        // given
        val pokemonDetail = PokemonDetail(
            id = 0,
            name = "pikacu",
            height = 9,
            weight = 9,
            moves = listOf(),
            types = listOf(),
            abilities = listOf(),
            imageUrl = "",
            stats = listOf()
        )
    }
}