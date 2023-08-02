package com.luqman.pokedex.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.pokedex.core.model.Resource
import com.luqman.pokedex.domain.usecase.CatchPokemonUseCase
import com.luqman.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.luqman.pokedex.domain.usecase.StorePokemonUseCase
import com.luqman.pokedex.ui.Destination.NAME_PARAMETER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val detailUseCase: GetPokemonDetailUseCase,
    private val catchUseCase: CatchPokemonUseCase,
    private val storeUseCase: StorePokemonUseCase
) : ViewModel() {

    private val selectedPokemonName: String? = state.get<String>(NAME_PARAMETER)

    private val _detailPokemon: MutableStateFlow<DetailScreenState> =
        MutableStateFlow(DetailScreenState())
    val detailPokemon = _detailPokemon.asStateFlow()

    private val _storeState: MutableStateFlow<StoreScreenState> =
        MutableStateFlow(StoreScreenState())
    val storeState = _storeState.asStateFlow()

    private val _catchState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val catchState = _catchState.asStateFlow()

    init {
        selectedPokemonName?.let {
            getPokemon(it)
        }
    }

    fun retry() {
        selectedPokemonName?.let { getPokemon(it) }
    }

    fun catch() {
        detailPokemon.value.data?.let {
            _catchState.value = catchUseCase.invoke()
        }
    }

    fun release() {
        _catchState.value = null
    }

    fun store(pokemonName: String) {
        detailPokemon.value.data?.let {
            storeUseCase.invoke(it, pokemonName).onEach { response ->
                when (response) {
                    is Resource.Success -> {
                        _storeState.value = StoreScreenState(success = true)
                        _catchState.value = null
                    }
                    is Resource.Loading -> {
                        _storeState.value = StoreScreenState(loading = true)
                    }
                    is Resource.Error -> {
                        _storeState.value = StoreScreenState(
                            success = false,
                            errorMessage = response.error
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getPokemon(name: String) {
        detailUseCase.invoke(name).onEach { response ->
            _detailPokemon.value = when (response) {
                is Resource.Success -> DetailScreenState(data = response.data)
                is Resource.Loading -> DetailScreenState(loading = true)
                is Resource.Error -> DetailScreenState(errorMessage = response.error)
            }
        }.launchIn(viewModelScope)
    }

}