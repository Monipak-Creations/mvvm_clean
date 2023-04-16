package com.example.machinetest.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.machinetest.domain.model.Product

class DetailViewModel constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    val product = savedStateHandle.getLiveData<Product>(PRODUCT_KEY)

    companion object {
        const val PRODUCT_KEY = "product_key"
    }

}