package com.luqman.pokedex.ui.mypokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.pokedex.core.model.Resource
import com.luqman.pokedex.data.repository.model.MyPokemon
import com.luqman.pokedex.domain.usecase.DeletePokemonUseCase
import com.luqman.pokedex.domain.usecase.GetMyPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPokemonListViewModel @Inject constructor(
    private val getMyPokemonListUseCase: GetMyPokemonListUseCase,
    private val deletePokemonUseCase: DeletePokemonUseCase
) : ViewModel() {

    val myPokemon = getMyPokemonListUseCase()

    private val _deleteState: MutableStateFlow<MyPokemonReleaseScreenState> =
        MutableStateFlow(MyPokemonReleaseScreenState())
    val deleteState = _deleteState.asStateFlow()

    fun release(id: Int) {
        deletePokemonUseCase.invoke(id).onEach { response ->
            _deleteState.value = when (response) {
                is Resource.Success -> MyPokemonReleaseScreenState(success = true)
                is Resource.Loading -> MyPokemonReleaseScreenState(loading = true)
                is Resource.Error -> MyPokemonReleaseScreenState(errorMessage = response.error)
            }
        }.launchIn(viewModelScope)
    }

    fun resetDeleteState() {
        _deleteState.value = MyPokemonReleaseScreenState()
    }
}