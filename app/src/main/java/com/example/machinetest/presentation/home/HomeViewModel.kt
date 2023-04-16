package com.example.machinetest.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.machinetest.app.util.Resource
import com.example.machinetest.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState = _homeUiState.asStateFlow()

    private var job: Job? = null

    init {
        getCategories()
    }

    private fun getCategories() {
        job?.cancel()
        job = viewModelScope.launch {
            categoryRepository.getCategories().collect {
                when (it) {
                    is Resource.Loading -> _homeUiState.emit(HomeUiState.Loading)
                    is Resource.Success -> _homeUiState.emit(HomeUiState.Success(it.value))
                    is Resource.Error -> _homeUiState.emit(HomeUiState.Error(it.error))
                }
            }
        }
    }

    fun retry() {
        getCategories()
    }
}