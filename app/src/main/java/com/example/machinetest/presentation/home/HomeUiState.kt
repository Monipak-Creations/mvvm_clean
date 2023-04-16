package com.example.machinetest.presentation.home

import com.example.machinetest.domain.model.Category

sealed class HomeUiState{
    object Idle: HomeUiState()
    object Loading: HomeUiState()
    data class Success(val categories : List<Category>) : HomeUiState()
    data class Error(val message : String) : HomeUiState()
}
