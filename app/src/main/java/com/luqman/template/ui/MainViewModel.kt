package com.luqman.template.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.template.data.repository.DataSource
import com.luqman.template.data.repository.model.Response
import com.luqman.template.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase,
    private val dataSource: DataSource
) : ViewModel() {

    private val _response = MutableStateFlow<List<Response>>(listOf())
    val response = _response.asStateFlow()

    fun search(query: String) {
        if (useCase(query).successful) {
            viewModelScope.launch {
                val data = dataSource.fetch()
                _response.value = data
            }
        }
    }
}